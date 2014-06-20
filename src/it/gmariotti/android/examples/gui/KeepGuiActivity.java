package it.gmariotti.android.examples.gui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.johnnyshieh.zoominanimation.BitmapStorageManager;
import com.johnnyshieh.zoominanimation.ZoomActivityHelper;
import com.nhaarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;

public class KeepGuiActivity extends Activity implements ActionMode.Callback {
    
    private List<Note> notes = new ArrayList<Note>();
    private Handler handler = new Handler();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_keep);
        getActionBar().setDisplayShowTitleEnabled(true);
        getActionBar().setIcon(R.drawable.ic_launcher_);
        
        initData();
        
        GridView gView = (GridView) findViewById(R.id.gridview);
        final CardsAnimationAdapter adapter = new CardsAnimationAdapter(new ItemAdapter());
        adapter.setAbsListView(gView);
        gView.setAdapter(adapter);
    }
    
    private void initData() {
        notes.add(new Note(R.drawable.shadow_blue, "今天下雨了！"));
        notes.add(new Note(R.drawable.shadow_yellow, "你带伞了嘛！"));
        notes.add(new Note(R.drawable.shadow_greyish_teal, "今天下雨了！"));
        notes.add(new Note(R.drawable.shadow_lichen, "今天下雨了！"));
        notes.add(new Note(R.drawable.shadow_magenta, "今天下雨了！"));
        notes.add(new Note(R.drawable.shadow_yellow, "今天下雨了！"));
        notes.add(new Note(R.drawable.shadow_greyish_teal, "今天下雨了！"));
        notes.add(new Note(R.drawable.shadow_magenta, "今天下雨了！"));
        notes.add(new Note(R.drawable.shadow_blue, "今天下雨了！"));
        notes.add(new Note(R.drawable.shadow_lichen, "今天下雨了！"));
        notes.add(new Note(R.drawable.shadow_yellow, "今天下雨了！"));
        notes.add(new Note(R.drawable.shadow_lichen, "今天下雨了！"));
        notes.add(new Note(R.drawable.shadow_lichen, "今天下雨了！"));
        notes.add(new Note(R.drawable.shadow_greyish_teal, "今天下雨了！"));
        notes.add(new Note(R.drawable.shadow_magenta, "今天下雨了！"));
        notes.add(new Note(R.drawable.shadow_lichen, "今天下雨了！"));
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.keep, menu);
        return true;
    }
    
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.detail_menu, menu);
        return true;
    }
    
    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }
    
    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.close_menu:
                mode.finish();
                fragment.closeSacle();
                return true;
            
            default:
                break;
        }
        return false;
    }
    
    @Override
    public void onDestroyActionMode(ActionMode mode) {
        
    }
    
    DetailFragment fragment = new DetailFragment();
    
    private void showDetail(final View originalView, Note note) {
        
        Bundle bundle = new Bundle();
        bundle.putInt("bgRes", note.getBgRes());
        bundle.putString("content", note.getContent());
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction().add(R.id.detail_container, fragment).commit();
        
        handler.postDelayed(new Runnable() {
            
            @Override
            public void run() {
                fragment.startScale(originalView);
            }
        }, 200);
        
    }
    
    public void removeDetail() {
        getFragmentManager().beginTransaction().remove(fragment).commit();
        
        closeOptionsMenu();
    }
    
    class CardsAnimationAdapter extends AnimationAdapter {
        private float mTranslationY = 400;
        
        private float mRotationX = 15;
        
        private long mDuration = 500;
        
        public CardsAnimationAdapter(BaseAdapter baseAdapter) {
            super(baseAdapter);
        }
        
        @Override
        protected long getAnimationDelayMillis() {
            return 30;
        }
        
        @Override
        protected long getAnimationDurationMillis() {
            return mDuration;
        }
        
        @Override
        public Animator[] getAnimators(ViewGroup parent, View view) {
            return new Animator[] { ObjectAnimator.ofFloat(view, "translationY", mTranslationY, 0),
                ObjectAnimator.ofFloat(view, "rotationX", mRotationX, 0) };
        }
    }
    
    class ItemAdapter extends BaseAdapter {
        
        @Override
        public int getCount() {
            return notes.size();
        }
        
        @Override
        public Object getItem(int position) {
            return null;
        }
        
        @Override
        public long getItemId(int position) {
            return 0;
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(KeepGuiActivity.this).inflate(
                    R.layout.gridview_item, null);
                holder.contentTV = (TextView) findViewById(R.id.content_tv);
                
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            
            final Note note = notes.get(position);
            
            convertView.setBackgroundResource(note.getBgRes());
            // holder.contentTV.setText(note.getContent());
            
            convertView.setOnClickListener(new OnClickListener() {
                
                @Override
                public void onClick(View v) {
//                    if(fragment.isAdded() || fragment.isRemoving() || fragment.isVisible() || fragment.getAnimating()){
//                        return;
//                    }
//                    showDetail(v, note);
//                    startActionMode(KeepGuiActivity.this);
                    
                    BitmapStorageManager.setCurrentBitmap(v);
                    Bundle bundle = new Bundle();
                    bundle.putInt("startX", (int) v.getX());
                    bundle.putInt("startY", (int) v.getY());
                    bundle.putBoolean("hasActionBar", true ) ;
                    bundle.putInt("animDirection", ZoomActivityHelper.SCALE_DIRECT );

                    Intent intent = new Intent(KeepGuiActivity.this,DemoActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            
            return convertView;
        }
        
    }
    
    static class ViewHolder {
        TextView contentTV;
    }
    
}
