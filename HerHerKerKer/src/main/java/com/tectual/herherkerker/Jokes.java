package com.tectual.herherkerker;

import de.greenrobot.event.EventBus;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.Options;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.Model;
import com.activeandroid.query.Select;
import com.google.api.client.util.DateTime;
import com.octo.android.robospice.GsonGoogleHttpClientSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.tectual.herherkerker.events.FlagEvent;
import com.tectual.herherkerker.events.JokeEvent;
import com.tectual.herherkerker.events.LikeEvent;
import com.tectual.herherkerker.events.ReplyEvent;
import com.tectual.herherkerker.events.UnlockableEvent;
import com.tectual.herherkerker.models.Joke;
import com.tectual.herherkerker.models.JokeAdapter;
import com.tectual.herherkerker.models.Reward;
import com.tectual.herherkerker.util.Core;
import com.tectual.herherkerker.web.JokeSpiceRequest;
import com.tectual.herherkerker.web.LikeJokeRequest;
import com.tectual.herherkerker.web.ListJokesRequestListener;
import com.tectual.herherkerker.web.ReplyRequest;
import com.tectual.herherkerker.web.ReplyRequestListener;
import com.tectual.herherkerker.web.UnlockableRequest;
import com.tectual.herherkerker.web.UnlockableRequestListener;
import com.tectual.herherkerker.web.VoidRequestListener;
import com.tectual.herherkerker.web.data.JsonReward;

import java.util.Date;
import java.util.List;

/**
 * Created by arash on 25/01/2014.
 */
public class Jokes implements
        OnRefreshListener {

    private SpiceManager spiceManager = new SpiceManager(GsonGoogleHttpClientSpiceService.class);
    private JokeSpiceRequest jokeRequest;
    private PullToRefreshLayout mPullToRefreshLayout;
    private Activity activity;
    private View view;
    private List<Model> list;
    private List<Reward> rewards;
    private JokeAdapter adapter;
    private Reward current_reward;

    public Jokes(Activity a, View v){
        activity = a;
        spiceManager.start(activity);
        view = v;
        mPullToRefreshLayout = (PullToRefreshLayout) view.findViewById(R.id.jokesFragment);
        ActionBarPullToRefresh.from(activity)
                .options(Options.create().scrollDistance(.50f).build())
                .theseChildrenArePullable(R.id.jokeslist)
                .listener(this)
                .setup(mPullToRefreshLayout);
        listJokes();
        HerherKerker app = ((HerherKerker) activity.getApplicationContext());
        EventBus.getDefault().register(this);
        if(app.is_first_load(activity.getResources().getInteger(R.integer.joke_tab))){
            load();
        }
    }

    public void load(){
        jokeRequest = new JokeSpiceRequest(Core.getInstance(activity));
        spiceManager.execute(jokeRequest, new ListJokesRequestListener());
    }

    private void listJokes(){
        if(mPullToRefreshLayout!=null)
            mPullToRefreshLayout.setRefreshComplete();
        ListView listview = (ListView) view.findViewById(R.id.jokeslist);
        list = new Select().from(Joke.class).orderBy("id DESC").limit(50).execute();

        if(list!=null){
            if(rewards!=null && !rewards.isEmpty()){
                list.add(2, rewards.get(0));
            }
            if(adapter!=null)
                adapter.clear();
            adapter = new JokeAdapter(activity,
                    android.R.layout.simple_list_item_1, list);
            listview.setAdapter(adapter);
        }
        /*listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                view.animate().setDuration(300).alpha(0)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                //list.remove(item);
                                adapter.notifyDataSetChanged();
                                view.setAlpha(1);
                            }
                        });
            }

        });*/
    }

    @Override
    public void onRefreshStarted(View view) {
        load();
    }

    public void onEvent(LikeEvent event){
        LinearLayout actions = (LinearLayout) event.view.getParent();
        TextView dislike = (TextView) actions.findViewById(R.id.dislike);
        TextView likes = (TextView) actions.findViewById(R.id.likes);
        likes.setText(event.joke.likes.toString());
        if(event.increased){
            event.view.setVisibility(View.GONE);
            dislike.setVisibility(View.VISIBLE);
            LikeJokeRequest request = new LikeJokeRequest(event.joke.sid);
            spiceManager.execute(request, new VoidRequestListener());
        }else{
            event.view.setVisibility(View.GONE);
            ((TextView) actions.findViewById(R.id.like)).setVisibility(View.VISIBLE);
        }
    }

    public void onEvent(FlagEvent event){
        //load();
    }


    public void onEvent(JokeEvent event){
        this.rewards = event.rewards;
        listJokes();
    }

    public void onEvent(UnlockableEvent event){
        current_reward = event.reward;
        UnlockableRequest request = new UnlockableRequest(event.reward.sid, Core.getInstance(activity));
        spiceManager.execute(request, new UnlockableRequestListener(activity));
    }

    public void onEvent(ReplyEvent event){
        JsonReward reward = current_reward.toJSON();
        ReplyRequest request = new ReplyRequest(event.question.id, Core.getInstance(activity), event.answer, reward);
        spiceManager.execute(request, new ReplyRequestListener());
    }
}
