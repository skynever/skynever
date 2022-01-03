import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
public class rockpaperscissor extends JFrame implements ActionListener {
	static final int ROCK=0;
	static final int PAPER=1;
	static final int SCISSOR=2;
	static final int RESULT=3;
	
	private JPanel panel;
	private JTextField output;
	private JTextField information;
	private JButton rock;
	private JButton paper;
	private JButton scissor;
	private JButton result;
	
	public rockpaperscissor() {
		setTitle("가위,바위,보");
		setSize(500,350);
		setDefalutOperation(EXIT_ON_CLOSE);
		panel=new JPanel();
		panel.setLayout(new GridLayout(0,4));
		information=new JTextField("아래 버튼 중에서 하나 클릭");
		output=new JTextField(20);
		rock=new JButton("ROCK");
		paper=new JButton("PAPER");
		scissor=new JButton("SCISSOR");
		result=new JButton("RESULT");
		rock.addActionListener(this);
		paper.addActionListener(this);
		scissor.addActionListener(this);
		result.addActionListener(this);
		
		panel.add(rock);
		panel.add(scissor);
		panel.add(paper);
		panel.add(result);
		add(information,BorderLayout.NORTH);
		add(panel,BorderLayout.CENTER);
		add(output,BorderLayout.SOUTH);
		setVisible(true);
	}
	private void setDefalutOperation(int exitOnClose) {
		// TODO Auto-generated method stub
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		rockpaperscissor gui=new rockpaperscissor();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Random random=new Random();
		int computer=random.nextInt(3);
		if(e.getSource()==rock) {
			if(computer==SCISSOR)
				output.setText("사용자 승리");
				else if(computer== ROCK)
					output.setText("비김");
				else
					output.setText("컴퓨터 승리");
			}else if(e.getSource()==paper) {
				if(computer==ROCK)
					output.setText("사용자승리");
				else if(computer==PAPER)
					output.setText("비김");
				else
					output.setText("컴퓨터 승리");
		}else if(e.getSource()==scissor) {
			if(computer==PAPER)
				output.setText("사용자 승리");
			else if(computer==SCISSOR)
				output.setText("비김");
			else
				output.setText("컴퓨터 승리");
		}
	}
}
