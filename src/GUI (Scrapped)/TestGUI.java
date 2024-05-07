//import javax.swing.*;
//import java.awt.*;
//
//public class TestGUI extends JFrame {
//    public TestGUI() {
//        super("Red Square GUI");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setSize(300, 300);
//        setLocationRelativeTo(null);
//        setVisible(true);
//
//        // Create a JPanel to draw on
//        JPanel panel = new JPanel() {
//            @Override
//            protected void paintComponent(Graphics g) {
//                super.paintComponent(g);
//                drawRedSquare(g);
//                drawLetterB(g);
//            }
//        };
//        getContentPane().add(panel);
//    }
//
//    private void drawRedSquare(Graphics g) {
//        g.setColor(Color.RED);
//        g.fillRect(50, 50, 100, 100);
//    }
//
//    private void drawLetterB(Graphics g) {
//        g.setColor(Color.WHITE);
//        g.setFont(new Font("Arial", Font.BOLD, 50));
//        g.drawString("B", 75, 100);
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(TestGUI::new);
//    }
//}
