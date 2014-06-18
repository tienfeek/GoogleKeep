/**
 * 
 */
package it.gmariotti.android.examples.gui;

/**
 * TODO
 * @author wangtianfei01
 *
 */
public class Note {
    
    private int bgRes;
    private String content;
    
    
    public Note(int bgRes, String content) {
        super();
        this.bgRes = bgRes;
        this.content = content;
    }
    public int getBgRes() {
        return bgRes;
    }
    public void setBgRes(int bgRes) {
        this.bgRes = bgRes;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    
    
    
}
