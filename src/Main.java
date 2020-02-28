/**
 *
 * @author Lu�s Matheus
 */

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.Random;

@SuppressWarnings("serial")
public class Main extends Applet implements Runnable, KeyListener {

	private Image i;
	private Graphics z;
	//Vetor de proj�teis
	Projeteis projeteis[];
	//Vetor de Aliens
	Aliens aliens[];
	//Nave Principa
	Nave nave;
	URL url;
	int numeroProjeteis, numeroAliens = 0, level = 0, vidaAliens=1;
	boolean atirando = false;
	double raioAliens = 25, menorVelocidadeAliens = 0.4, maiorVelocidadeAliens = 5;

	//Tnicializar
	@Override
	public void init() {
		//Tamanho da tela
		setSize(1200, 600);
		//Numero maximo de proj�teis na tela
		projeteis = new Projeteis[50]; 
		url = getDocumentBase();
		new ImagensSons(this);
	}

	@Override
	public void start() {
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void paint(Graphics g) {
		//Plano de fundo
		setBackground(Color.BLACK);
		//Desenhar a nave
		nave.imprimir(g);
		//Desenhar os proj�teis
		for (int i = 0; i < numeroProjeteis; i++) {
			projeteis[i].imprimir(g);
		}
		//Desenhar os aliens
		for (int i = 0; i < numeroAliens; i++) {
				aliens[i].imprimir(g);
		}
		//Exibir level atual
		g.setColor(Color.WHITE);
		g.drawString("Level " + level, 20, 20);	
	}

	@Override
	public void update(Graphics g) {
		if (i == null) {
			i = createImage(this.getSize().width, this.getSize().height);
			z = i.getGraphics();
		}
		z.setColor(getBackground());
		z.fillRect(0, 0, this.getSize().width, this.getSize().height);
		z.setColor(getForeground());
		paint(z);
		g.drawImage(i, 0, 0, this);
	}

	@Override
	public void run() {
		while (true) {
			
			//iniciar novo level
			if (numeroAliens == 0) {
				proxlvl();
			}
			
			//Mover a nave
			nave.mover(this.getWidth(), this.getHeight());	
			
			//Mover os proj�teis
			for (int i = 0; i < numeroProjeteis; i++) {	
				projeteis[i].mover(this.getWidth(), this.getHeight());
				
				//Apagar projet�is caso n�o acertem
				if (projeteis[i].erro <= 0) {
					apagarProjetil(i);
					i--;
				}
			}
			
			//Recalcula a colis�o
			bufferAliens();	
			
			//Verificar se � possivel atirar
			if (atirando && nave.podeAtirar()) {
				projeteis[numeroProjeteis] = nave.atirar();
				numeroProjeteis++;
			}
			repaint();
			try {
				Thread.sleep(17);
			} catch (InterruptedException ex) {
			}
		}
	}

	public void apagarProjetil(int v) {
		numeroProjeteis--;
		for (int i = v; i < numeroProjeteis; i++)
			projeteis[i] = projeteis[i + 1];
		projeteis[numeroProjeteis] = null;
	}

	public void deletarAlien(int z) {
		numeroAliens--;
		Random random = new Random();
		int a = random.nextInt(3);
		switch (a) {		//Seleciona a explosao aleat�riamente
		case 0:
			ImagensSons.explosao1.play();
			break;
		case 1:
			ImagensSons.explosao2.play();
			break;
		case 2:
			ImagensSons.explosao3.play();
			break;
		default:
			break;
		}
		for (int i = z; i < numeroAliens; i++)
			aliens[i] = aliens[i + 1];
		aliens[numeroAliens] = null;
	}

	//Verificar a colis�o
	public void bufferAliens() {
		//Mover Aliens
		for (int i = 0; i < numeroAliens; i++) {
			aliens[i].mover(this.getWidth(), this.getHeight());
			//Colis�o da Nave no Alien
			if (aliens[i].colisaoNave(nave)) {
				ImagensSons.reiniciar.play();
				level--;
				numeroAliens = 0;
				return;
			}
			for (int j = 0; j < numeroProjeteis; j++) {
				//Colis�o do Proj�til na Nave
				if (aliens[i].colisaoProjetil(projeteis[j])) {
					apagarProjetil(j);
					deletarAlien(i);
					j = numeroProjeteis;
					i--;
				}
			}
		}
	}

	//Preparar pr�ximo level
	public void proxlvl() {
		level++;
		if(level == 1){
			ImagensSons.comecar.play();
		}
		if (level > 1){
			ImagensSons.novolevel.play();
		}
		//inicializador da Nave(X inicial, Y inicial, �ngulo Inicial, Acelera��o, Velocidade Rotacional,Cad�ncia)
		nave = new Nave(600, 300, 0, 0.080, 0.10, 10);
		numeroProjeteis = 0;
		atirando = false;
		addKeyListener(this);
		//Formula para numero de Aliens por level
		aliens = new Aliens[level * vidaAliens + 1];
		numeroAliens = level;
		for (int i = 0; i < numeroAliens; i++){
			//Inicializador dos vetor Aliens & Formula da movimenta��o aleat�ria dos Aliens
			aliens[i] = new Aliens(Math.random() * this.getWidth(),Math.random() * this.getHeight(), raioAliens, menorVelocidadeAliens,maiorVelocidadeAliens, vidaAliens);
		}
		   
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			nave.acelerar = true;
			ImagensSons.acelerar.loop();
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			nave.girarEsquerda = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			nave.girarDireita = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
			atirando = true;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			nave.acelerar = false;
			ImagensSons.acelerar.stop();
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			nave.girarEsquerda = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			nave.girarDireita = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
			atirando = false;
		}
	}

}
