package Graphique;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;



public class keyDemoKeyListener implements KeyListener {


    private InterfacesDeJeu  m_window;


    public keyDemoKeyListener(InterfacesDeJeu  window){
        this.m_window = window ;
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
            
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
    	
    		int var =arg0.getKeyCode() ;
    		
    		 switch( var) {
             case KeyEvent.VK_DOWN: 
            	 m_window.moveBas();
            	 
            	 break;
             case KeyEvent.VK_UP: 
            	 
            	 m_window.moveHaut();
            	 break;
             case KeyEvent.VK_LEFT:
            
            	 m_window.movegauche();
            	 break;
             case KeyEvent.VK_RIGHT:
            	
            	 m_window.moveDroite();
            	 break;
           
         }
    		
    		
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        
    } 

}