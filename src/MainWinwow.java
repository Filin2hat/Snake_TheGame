import javax.swing.*;

public class MainWinwow extends JFrame {
    public MainWinwow (){  //основное окно
        setTitle("Змейка by Filinhat v0.1"); //заголовок окна
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //действие при нажатии крестика
        setSize(352,376); //размер окна
        setLocation(400,400); // нулевая точка окна??
        add(new GameField()); // добавляем на поле экземпляр объекта GameField
        setVisible(true); //видимость окна
    }

    public static void main(String[] args) {
        MainWinwow mw = new MainWinwow();

    }
}
