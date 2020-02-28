
import java.awt.Color;
import java.awt.Graphics;

public class Projeteis {
	// Velocidade do projetil na tela
	int velocidade = 4;
	// tempo na tela antes de desaparecer
	int erro;
	double x, y, vx, vy;

	// Construtor
	public Projeteis(double x2, double y2, double vx2, double vy2,
			double angulo, int erro) {
		this.x = x2;
		this.y = y2;
		this.erro = erro;
		// Formula da trajetoria
		vx = velocidade * Math.cos(angulo) + vx2;
		vy = velocidade * Math.sin(angulo) + vy2;
	}

	public void imprimir(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawOval((int) x - 5, (int) y - 5, 3, 3);

	}

	public void mover(int comprimento, int altura) {
		// Diminui o tempo do projetil na tela
		erro--;
		x += vx;
		y += vy;
		// Transporta o projetil pro lado oposto caso alcance o limite da tela
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

}
