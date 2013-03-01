/**
Khalid
*/
package org.sikuli.slides.shapes;

import java.io.File;

import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.sikuli.slides.sikuli.SikuliController;
import org.sikuli.slides.utils.Constants;

public class Oval extends Shape {
	private int width;
	private int height;
	
	public Oval(String name,String id, int offx, int offy, int cx,  int cy, int width, int height,String text, int order){
		super(id,name,offx,offy,cx,cy,text,order);
		this.width=width;
		this.height=height;
	}
	public Oval(String id, String name,int order) {
		super(id,name,0,0,0,0,"",order);
		this.width=0;
		this.height=0;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String toString(){
		return "Oval info:\n" +super.toString()+
				"\n width:"+width+"\n height:"+height+
				"\n ******************************************";
	}
	/**
	 * perform right click on the target image.
	 * @imageName the target image full path name.
	 */
	@Override
	public void doSikuliAction(File targetFile) {
		final ImageTarget imageTarget=new ImageTarget(targetFile);
		if(imageTarget!=null){
			ScreenRegion fullScreenRegion=new DesktopScreenRegion();
			ScreenRegion targetRegion=fullScreenRegion.wait(imageTarget,Constants.MaxWaitTime);
			if(targetRegion!=null){
				Mouse mouse = new DesktopMouse();
				mouse.rightClick(targetRegion.getCenter());
				SikuliController.displayBox(targetRegion);
			}
			else
				System.err.println("Couldn't find target on the screen.");
		}
	}
}
