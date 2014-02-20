package com.tectual.herherkerker;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ScrollView;

import com.activeandroid.query.Select;
import com.octo.android.robospice.SpiceManager;
import com.tectual.herherkerker.events.DeleteEvent;
import com.tectual.herherkerker.events.RedeemEvent;
import com.tectual.herherkerker.events.RewardsEvent;
import com.tectual.herherkerker.events.UseEvent;
import com.tectual.herherkerker.models.Reward;
import com.tectual.herherkerker.models.RewardAdapter;
import com.tectual.herherkerker.util.Core;
import com.tectual.herherkerker.web.Rewards.GetRewards;
import com.tectual.herherkerker.web.Rewards.GetRewardsListener;
import com.tectual.herherkerker.web.Rewards.SyncRewards;
import com.tectual.herherkerker.web.Rewards.SyncRewardsListener;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.Options;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;

/**
 * Created by arash on 13/02/2014.
 */
public class Wallet implements
        OnRefreshListener {

    private PullToRefreshLayout mPullToRefreshLayout;

    private MainActivity activity;
    private View view;
    private SpiceManager spiceManager;
    private List<Reward> list;
    public Reward current;
    private RewardAdapter adapter;

    public Wallet(MainActivity a, View v){
        activity = a;
        view = v;
        spiceManager = activity.spiceManager;
        EventBus.getDefault().register(this);
        mPullToRefreshLayout = (PullToRefreshLayout) view.findViewById(R.id.walletFragment);
        ActionBarPullToRefresh.from(activity)
                .options(Options.create().scrollDistance(.50f).build())
                .theseChildrenArePullable(R.id.rewardslist)
                .listener(this)
                .setup(mPullToRefreshLayout);
        HerherKerker app = ((HerherKerker) activity.getApplicationContext());
        listRewards();
        if(app.is_first_load(activity.getResources().getInteger(R.integer.wallet_tab))){
            load();
            List<Reward> changed_rewards = new Select().from(Reward.class).where("is_changed=1").execute();
            SyncRewards request = new SyncRewards(Core.getInstance(activity), changed_rewards);
            spiceManager.execute(request, new SyncRewardsListener(changed_rewards));
        }
    }

    private void listRewards(){
        if(mPullToRefreshLayout!=null)
            mPullToRefreshLayout.setRefreshComplete();
        ListView listview = (ListView) view.findViewById(R.id.rewardslist);
        String now = String.valueOf(DateTime.now().getMillis());
        String past = String.valueOf(DateTime.now().minusDays(10).getMillis());
        list = new Select().from(Reward.class).where("(state IN ('collected', 'draw') AND expires_at > "+now+") OR (state = 'won' AND expires_at > "+past+")").orderBy("expires_at ASC").limit(50).execute();

        if(list!=null){
            ScrollView intro = (ScrollView) view.findViewById(R.id.wallet_intro);
            if(intro!=null){
                ViewGroup container = (ViewGroup) intro.getParent();
                container.removeView(intro);
            }

            if(adapter!=null)
                adapter.clear();
            adapter = new RewardAdapter(activity,
                    android.R.layout.simple_list_item_1, list);
            listview.setAdapter(adapter);
        }
    }

    @Override
    public void onRefreshStarted(View view) {
        load();
    }

    public void load(){
        GetRewards rewardsRequest = new GetRewards(Core.getInstance(activity));
        spiceManager.execute(rewardsRequest, new GetRewardsListener());
    }

    public void onEvent(RewardsEvent event){
        listRewards();
    }

    public void onEvent(DeleteEvent event){
        Reward reward = event.reward;
        reward.state = Reward.DELETE;
        Vanished(reward, event.position);
    }

    public void onEvent(UseEvent event){
        Reward reward = event.reward;
        reward.state = Reward.USED;
        Vanished(reward, event.position);
    }

    private void Vanished(Reward reward, int position){
        reward.is_changed = true;
        reward.save();
        list.remove(position);
        adapter.notifyDataSetChanged();
        List<Reward> rewards = new ArrayList<Reward>();
        rewards.add(reward);
        SyncRewards request = new SyncRewards(Core.getInstance(activity), rewards);
        spiceManager.execute(request, new SyncRewardsListener(rewards));
    }

    public void onEvent(RedeemEvent event){
        if (event.is_won){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setType("message/rfc822");
            String body = String.format(view.getContext().getString(R.string.won_email_text), Core.getInstance(activity).device_id);
            Uri data = Uri.parse("mailto:app@tectual.com.au?subject=" + view.getContext().getString(R.string.won_email_title) + "&body=" + body);
            intent.setData(data);
            view.getContext().startActivity(intent);
        }else {
            current = event.reward;
            new PopupPageBuilder(activity, R.layout.instruction, activity.getString(R.string.reward_info), event);
        }
    }

    public void Unregister(){
        EventBus.getDefault().unregister(this);
    }
}
