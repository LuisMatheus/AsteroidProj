import java.applet.AudioClip;
import java.awt.Image;
import java.net.URL;


public class ImagensSons {
	static Image alien,alien2,alien3,alien4,alien5,alien6;
	static AudioClip atirar,acelerar,reiniciar,comecar,novolevel,explosao1,explosao2,explosao3;
	URL url;
	static Main m;

	//Construtor
	@SuppressWarnings("static-access")
	public ImagensSons(Main m) {
		url = m.getCodeBase();
		this.m = m;
		
		//Sons da nave
		atirar = m.getAudioClip(url, "Sons/fire.wav");
		acelerar = m.getAudioClip(url, "Sons/thrust.wav");
		reiniciar = m.getAudioClip(url, "Sons/beat1.wav");
		comecar = m.getAudioClip(url, "Sons/start.wav");
		novolevel = m.getAudioClip(url,"Sons/level.wav");
		
		//Sons das explos�es
		explosao1 = m.getAudioClip(url, "Sons/bangSmall.wav");
		explosao2 = m.getAudioClip(url, "Sons/bangMedium.wav");
		explosao3 = m.getAudioClip(url, "Sons/bangLarge.wav");
		
		//Imagens dos aliens
		alien = m.getImage(url, "images/alien.gif");
		alien2 = m.getImage(url, "images/alien2.gif");
		alien3 = m.getImage(url, "images/alien3.gif");
		alien4 = m.getImage(url, "images/alien4.gif");
		alien5 = m.getImage(url, "images/alien5.png");
		}

	}
