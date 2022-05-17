import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int SIZE = 320; //размер игрового поля
    private final int DOT_SIZE = 16; // размер одной точки на поле (сегмента змейки)
    private final int ALL_DOTS = 400; // максимальное кол-во сегментов на поле (320/16=20, 20*20=400)
    private Image dot;
    private Image apple;
    private int appleX;
    private int appleY;
    private int[] x = new int[ALL_DOTS]; // массивы для
    private int[] y = new int[ALL_DOTS]; // хранения положения змейки
    private int dots; // Переменная для хранения размера змейки
    private Timer timer;
    private boolean left = false; // Булевы поля
    private boolean right = true; // отвечающие за
    private boolean up = false;   // текущее направление
    private boolean down = false; // движения змейки.
    private boolean inGame = true;     //В игре или уже слились?

    public GameField() { //конструктор
        setBackground(Color.black);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    public void initGame() { // метод начальной инициализации змейки на поле
        dots = 3; // начальная длина змейки
        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i * DOT_SIZE;  // 48 кратно 16ти. Как и все остальные значения в игре.
            y[i] = 48;
        }
        timer = new Timer(250, this); // таймер (тики в игре) скорость игры
        timer.start();
        createApple();
    }

    public void createApple() { //метод генерации яблок на поле
        appleX = new Random().nextInt(20) * DOT_SIZE;
        appleY = new Random().nextInt(20) * DOT_SIZE;
    }


    public void loadImages() {  //метод загрузки картинок
        ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("dot.png");
        dot = iid.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {  //метод отрисовки
        super.paintComponent(g);
        if (inGame) {
            g.drawImage(apple, appleX, appleY, this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot, x[i], y[i], this);
            }
        } else {
            String str = "GAME OVER";
            Font font = new Font("Arial", Font.BOLD, 20);
            g.setColor(Color.orange);
            g.setFont(font);
            g.drawString(str, 115, SIZE / 2);
        }
    }

    public void move() { //метод движения
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (left) {
            x[0] -= DOT_SIZE;
        }
        if (right) {
            x[0] += DOT_SIZE;
        }
        if (up) {
            y[0] -= DOT_SIZE;
        }
        if (down) {
            y[0] += DOT_SIZE;
        }
    }

    public void checkApple() { // метод "поедания" яблок
        if (x[0] == appleX && y[0] == appleY) {
            dots++;
            createApple();
        }
    }

    public void checkCollisions() { // проверка столкновения
        for (int i = dots; i > 0; i--) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
                inGame = false;
            }
        }
        if (x[0] > SIZE) {
            inGame = false;
        }
        if (x[0] < 0) {
            inGame = false;
        }
        if (y[0] > SIZE) {
            inGame = false;
        }
        if (y[0] < 0) {
            inGame = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollisions();
            move();
        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter { //класс добавления управления с клавиатуры
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT && !right) {
                left = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_RIGHT && !left) {
                right = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_UP && !down) {
                left = false;
                up = true;
                right = false;
            }
            if (key == KeyEvent.VK_DOWN && !up) {
                left = false;
                down = true;
                right = false;
            }
        }
    }
}
