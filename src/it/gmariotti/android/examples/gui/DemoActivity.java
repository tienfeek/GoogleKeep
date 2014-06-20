/*
 * Copyright (C) 2013 Johnny Shieh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.gmariotti.android.examples.gui;

import com.johnnyshieh.zoominanimation.*;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

/**
 * @ClassName:  DemoActivity
 * @Description:TODO
 * @author  Johnny Shieh
 * @date    December 26, 2013
 */
public class DemoActivity extends ZoomInActivity{
    
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        
        setContentView(R.layout.activity_demo);
        this.mAnimating = true ;
        View view = findViewById ( R.id.textView ) ;
        ImageView imageView = (ImageView) findViewById ( R.id.imageView ) ;
        this.mZoomingActivityHelper = new ZoomActivityHelper ( this.getIntent().getExtras(), imageView, view, this, this ) ;
        
//        getActionBar().setDisplayShowTitleEnabled(true);
    }
    
    protected void onSetActionBar () {
        getActionBar().setDisplayOptions( ActionBar.DISPLAY_SHOW_TITLE) ;
    }
    
    
    public boolean onOptionsItemSelected ( MenuItem item ) {
    	switch ( item.getItemId() ) {
    	case android.R.id.home :
    		finish ();
    		return true ;
    	default :
    		return super.onOptionsItemSelected(item) ;
    	}
    }
}
