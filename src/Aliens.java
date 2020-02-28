import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

public class Aliens {
	double x, y, vx, vy, raio;
	int vida, i;
	Image alien;
	Random random = new Random();

	// Construtor
	public Aliens(double x, double y, double raio, double velmin,double velmax, int vida) {
		this.x = x;
		this.y = y;
		this.vida = vida;
		this.raio = raio;
		// Velocidade aleatoria
		double vel = velmin + Math.random() * (velmax - velmin);
		// Direcao aleatoria
		double dir = 2 * Math.PI * Math.random();
		vx = vel * Math.cos(dir);
		vy = vel * Math.sin(dir);
		// Escolhe os aliens aleat�riamente
		i = random.nextInt(5);
		switch (i) {
		case 0:
			alien = ImagensSons.alien;
			break;
		case 1:
			alien = ImagensSons.alien2;
			break;
		case 2:
			alien = ImagensSons.alien3;
			break;
		case 3:
			alien = ImagensSons.alien4;
			break;
		case 4:
			alien = ImagensSons.alien5;
			break;
		default:
			break;
		}

	}

	public void mover(int comprimento, int altura) {
		x += vx;
		y += vy;
		// Caso alcance o limite da tela e transportado para o lado oposto
		if (x < 0 - raio) {
			x += comprimento + 2 * raio;
		}
		if (x > comprimento + raio) {
			x -= comprimento + 2 * raio;
		}
		if (y < 0 - raio) {
			y += altura + 2 * raio;
		}
		if (y > altura + raio) {
			y -= altura + 2 * raio;
		}
	}

	public void imprimir(Graphics g) {
		g.drawImage(alien, (int) (x - raio + .5), (int) (y - raio + .5),(int) (2 * raio), (int) (2 * raio), ImagensSons.m);
	}

	// Retorna o valor restante da vida do Alien
	public int vidaRestante() {
		return vida;
	}

	// Verifica a colis�o do Alien com a nave
	public boolean colisaoNave(Nave main) {
		// Detecta a Colisao apartir do raio do Alien
		if (Math.pow(raio + main.raio, 2) > Math.pow(main.x - x, 2)+ Math.pow(main.y - y, 2)) {
			return true;
		} else {
			return false;
		}
	}

	// Verifica a colisao do Alien com um Projetil
	public boolean colisaoProjetil(Projeteis proj) {
		// Detecta a colisao a partir do raio do Alien
		if (Math.pow(raio, 2) > Math.pow(proj.x - x, 2)+ Math.pow(proj.y - y, 2)) {
			return true;
		} else {
			return false;
		}
	}
}
