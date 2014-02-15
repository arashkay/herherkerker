package com.tectual.herherkerker;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ListView;

import com.activeandroid.query.Select;
import com.octo.android.robospice.SpiceManager;
import com.tectual.herherkerker.events.RedeemEvent;
import com.tectual.herherkerker.events.RewardsEvent;
import com.tectual.herherkerker.models.Reward;
import com.tectual.herherkerker.models.RewardAdapter;
import com.tectual.herherkerker.util.Core;
import com.tectual.herherkerker.web.RewardsRequest;
import com.tectual.herherkerker.web.RewardsRequestListener;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
            //load();
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
        RewardsRequest rewardsRequest = new RewardsRequest(Core.getInstance(activity));
        spiceManager.execute(rewardsRequest, new RewardsRequestListener());
    }


    public void onEvent(RewardsEvent event){
        listRewards();
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
            listRewards();
        }
    }
}
