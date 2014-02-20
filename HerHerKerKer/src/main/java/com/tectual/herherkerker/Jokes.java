package com.tectual.herherkerker;

import de.greenrobot.event.EventBus;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.Options;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.Model;
import com.activeandroid.query.Select;
import com.octo.android.robospice.SpiceManager;
import com.tectual.herherkerker.events.FlagEvent;
import com.tectual.herherkerker.events.JokeEvent;
import com.tectual.herherkerker.events.LikeEvent;
import com.tectual.herherkerker.events.NewJokeEvent;
import com.tectual.herherkerker.events.ReplyEvent;
import com.tectual.herherkerker.events.UnlockableEvent;
import com.tectual.herherkerker.events.UnlockedEvent;
import com.tectual.herherkerker.models.Joke;
import com.tectual.herherkerker.models.JokeAdapter;
import com.tectual.herherkerker.models.Reward;
import com.tectual.herherkerker.util.Core;
import com.tectual.herherkerker.util.Storage;
import com.tectual.herherkerker.web.Jokes.CreateJoke;
import com.tectual.herherkerker.web.Jokes.GetJokes;
import com.tectual.herherkerker.web.Jokes.GetJokesListener;
import com.tectual.herherkerker.web.Jokes.LikeJoke;
import com.tectual.herherkerker.web.Replies.CreateReply;
import com.tectual.herherkerker.web.Replies.CreateReplyListener;
import com.tectual.herherkerker.web.Rewards.UnlockReward;
import com.tectual.herherkerker.web.Rewards.UnlockRewardListener;
import com.tectual.herherkerker.web.VoidRequestListener;
import com.tectual.herherkerker.web.data.JsonReward;

import java.util.List;

/**
 * Created by arash on 25/01/2014.
 */
public class Jokes implements
        OnRefreshListener {

    private PullToRefreshLayout mPullToRefreshLayout;
    private MainActivity activity;
    private View view;
    private SpiceManager spiceManager;
    private List<Model> list;
    private List<Reward> rewards;
    private JokeAdapter adapter;
    private Reward current_reward;

    public Jokes(MainActivity a, View v){
        activity = a;
        view = v;
        spiceManager = activity.spiceManager;
        EventBus.getDefault().register(this);
        mPullToRefreshLayout = (PullToRefreshLayout) view.findViewById(R.id.jokesFragment);
        ActionBarPullToRefresh.from(activity)
                .options(Options.create().scrollDistance(.50f).build())
                .theseChildrenArePullable(R.id.jokeslist)
                .listener(this)
                .setup(mPullToRefreshLayout);
        listJokes();
        HerherKerker app = ((HerherKerker) activity.getApplicationContext());
        //
        if(app.is_first_load(activity.getResources().getInteger(R.integer.joke_tab))){
            load();
        }
    }

    public void load(){
        int last_reward = Storage.getInstance(activity).rewards();
        GetJokes jokeRequest = new GetJokes(Core.getInstance(activity), last_reward);
        spiceManager.execute(jokeRequest, new GetJokesListener());
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
            LikeJoke request = new LikeJoke(event.joke.sid);
            spiceManager.execute(request, new VoidRequestListener());
        }else{
            event.view.setVisibility(View.GONE);
            ((TextView) actions.findViewById(R.id.like)).setVisibility(View.VISIBLE);
        }
    }

    public void onEvent(FlagEvent event){
        event.joke.delete();
        list.remove(event.position);
        adapter.notifyDataSetChanged();
    }

    public void onEvent(JokeEvent event){
        this.rewards = event.rewards;
        listJokes();
    }

    public void onEvent(NewJokeEvent event){
        Toast.makeText(activity.getApplicationContext(), R.string.joke_is_posted,
                Toast.LENGTH_LONG).show();
        CreateJoke request = new CreateJoke(Core.getInstance(activity), event.joke);
        spiceManager.execute(request, new VoidRequestListener());
    }

    public void onEvent(UnlockedEvent event){
        list.remove(2);
        adapter.notifyDataSetChanged();
        Toast.makeText(activity.getApplicationContext(), R.string.unlocked,
                Toast.LENGTH_LONG).show();
    }

    public void onEvent(UnlockableEvent event){
        if(event.accepted){
            current_reward = event.reward;
            UnlockReward request = new UnlockReward(event.reward.sid, Core.getInstance(activity));
            spiceManager.execute(request, new UnlockRewardListener(activity));
        }else{
            list.remove(2);
            adapter.notifyDataSetChanged();
            Storage.getInstance(activity).rewards(event.reward.sid);
            Toast.makeText(activity.getApplicationContext(), R.string.rejected,
                    Toast.LENGTH_LONG).show();
        }
    }

    public void onEvent(ReplyEvent event){
        JsonReward reward = current_reward.toJSON();
        CreateReply request = new CreateReply(event.question.id, Core.getInstance(activity), event.answer, reward);
        spiceManager.execute(request, new CreateReplyListener());
    }

    public void Unregister(){
        EventBus.getDefault().unregister(this);
    }

}
