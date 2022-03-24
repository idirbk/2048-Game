package Graphique;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyMenuListner implements KeyListener {

	 private Menu m_window;
	  public KeyMenuListner(Menu  window){
	        this.m_window = window ;
	  }

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		int var =arg0.getKeyCode() ;
		
		 switch(var) {
        case KeyEvent.VK_LEFT:
       
       	 	m_window.sub();
       	 	break;
        case KeyEvent.VK_RIGHT:
        	m_window.add();
       	 	break;
        case KeyEvent.VK_ENTER :
	       	 m_window.enter();
	       	 break;
      
		 }

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
