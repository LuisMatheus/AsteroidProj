import java.awt.Color;
import java.awt.Graphics;

public class Nave {

	public double[] origemX = { 14, -10, -6, -10 }, origemY = { 0, -8, 0, 8 };
	public int[] pontosX = new int[4], pontosY = new int[4]; // Posicao relativa
																// da nave
	final int raio = 6;
	double x, y, vx = 0, vy = 0, anguloInicial, aceleracao, velRotacional;
	boolean girarEsquerda = false;
	boolean girarDireita = false;
	boolean acelerar = false;
	// Velocidade entre disparos
	int cadencia;
	// Tempo entre disparos
	int recuo;

	// Construtor
	public Nave(double x, double y, double anguloInicial, double aceleracao,
			double velocidadeRotacional, int cadencia) {
		this.x = x;
		this.y = y;
		this.anguloInicial = anguloInicial;
		this.aceleracao = aceleracao;
		this.velRotacional = velocidadeRotacional;
		this.cadencia = cadencia;
	}

	public void imprimir(Graphics g) {
		for (int i = 0; i < 4; i++) {
			// Formula da nave
			pontosX[i] = (int) (origemX[i] * Math.cos(anguloInicial)- origemY[i] * Math.sin(anguloInicial) + x + .5);
			pontosY[i] = (int) (origemX[i] * Math.sin(anguloInicial)
					+ origemY[i] * Math.cos(anguloInicial) + y + .5);
		}
		g.setColor(Color.WHITE);
		g.fillPolygon(pontosX, pontosY, 4);
	}

	public void mover(int comprimento, int altura) {
		if (girarEsquerda) {
			anguloInicial -= velRotacional;
		}
		if (girarDireita) {
			anguloInicial += velRotacional;
		}
		if (anguloInicial > (2 * Math.PI)) {
			anguloInicial -= (2 * Math.PI);
		}
		if (anguloInicial < 0) {
			anguloInicial += (2 * Math.PI);
		}
		// Reduz o recuo
		if (recuo > 0) {
			recuo--;
		}
		if (acelerar) {
			// Limitador de velocidade do eixo X
			if (vx > 20) {
				vx = 20;
			}
			// Formula de rotacao no eixo X
			vx += aceleracao * Math.cos(anguloInicial);
			// Limitador de velocidade do eixo Y
			if (vy > 20) {
				vy = 20;
			}
			// Formula de rotacao no eixo Y
			vy += aceleracao * Math.sin(anguloInicial);
		}
		x += vx;
		y += vy;
		// transporta a nave para o lado oposto caso alcance o limite da tela
		if (x < 0) {
			x += comprimento;
		}
		if (x > comprimento) {
			x -= comprimento;
		}
		if (y < 0) {
			y += altura;
		}
		if (y > altura) {
			y -= altura;
		}
	}

	// Verifica se o recuo permite atirar
	public boolean podeAtirar() {
		if (recuo > 0) {
			return false;
		} else {
			return true;
		}
	}

	public Projeteis atirar() {
		// Gera o recuo
		recuo = cadencia;
		ImagensSons.atirar.play();
		// Construtor de projeteis (x inicial, y inicial, vx, vy, angulo da nave,tempo de dasaparecimento do projetil)
		return new Projeteis(x, y, vx, vy, anguloInicial, 70);
	}

}
