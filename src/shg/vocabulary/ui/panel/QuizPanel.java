package shg.vocabulary.ui.panel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import shg.vocabulary.exception.MakeQuizException;
import shg.vocabulary.object.EnglishVoca;
import shg.vocabulary.object.Quiz;
import shg.vocabulary.object.QuizTest;
import shg.vocabulary.object.Word;
import shg.vocabulary.object.tool.MakeQuiz;
import shg.vocabulary.object.tool.OpenedDictionary;
import shg.vocabulary.object.tool.PrintToGUI;

@SuppressWarnings("serial")
public class QuizPanel extends JPanel {
	private JButton startButton;
	private JPanel home = new JPanel();
	private QuizResultPanel result = new QuizResultPanel();
	private List<JPanel> panels;
	private CardLayout card = new CardLayout();
	private QuizTest test;
	
	public QuizPanel() {
		this.setLayout(card);
		
		startButton = new JButton("객관식 퀴즈 시작");
		home.add(startButton);
		resetPanels();
		
		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					MakeQuiz makeQuiz = new MakeQuiz(OpenedDictionary.getInstance());
					List<Quiz> quizList = new ArrayList<>();
					
					for (int i=0; i < 10; i++)
						quizList.add(makeQuiz.make(4));
					
					QuizTest test = new QuizTest(quizList);
					PrintToGUI ptg = new PrintToGUI();
					QuizPanel.this.test = test;
					ptg.quizStart(test, QuizPanel.this);
					card.next(QuizPanel.this);
				} catch (MakeQuizException ex) {
					JOptionPane.showMessageDialog(null, "단어가 부족해 퀴즈를 만들 수 없습니다.", "Message", JOptionPane.WARNING_MESSAGE);
					return;
				}
			}
			
		});
	}
	
	private void addResultPanel() {
		this.result = new QuizPanel.QuizResultPanel();
		this.getPanels().add(this.result);
	}
	
	private void resetPanels() {
		try {
			EnglishVoca dictionary = (EnglishVoca) OpenedDictionary.getInstance();
			dictionary.update();
		} catch(NullPointerException e) {}
		panels = new ArrayList<>();
		panels.add(home);
		setPanels();
		test = null;
		result = null;
	}
	
	private void setPanels() {
		this.removeAll();
		int num = 1;
		for (JPanel panel : panels) {
			this.add(panel, Integer.toString(num));
			num++;
		}
	}
	
	private List<JPanel> getPanels() {
		return this.panels;
	}
	
	public void settingStartQuizPanel() {
		for (int i=0; i<test.quizList.size(); i++) {
			if (i == test.quizList.size()-1)
				this.getPanels().add(this.createQuizTestPanel(i+1, test.quizList.get(i), true));
			else
				this.getPanels().add(this.createQuizTestPanel(i+1, test.quizList.get(i), false));
				
		}
		
		this.addResultPanel();
		this.setPanels();
	}
	
	private JPanel createQuizTestPanel(int number, Quiz quiz, boolean lastQuiz) {
		return new QuizTestPanel(number, quiz, lastQuiz);
	}
	
	
	private class QuizTestPanel extends JPanel {
		private JPanel south = new JPanel();
		private ButtonGroup group = new ButtonGroup();
		private JRadioButton[] radioButions;
		private JButton nextButtion;
		private JLabel question;
		
		public QuizTestPanel(int number, Quiz quiz, boolean lastQuiz) {
			QuizPanel quizPanel = QuizPanel.this;
			this.radioButions = new JRadioButton[quiz.choiceSet.size()];
			this.setLayout(new BorderLayout());

			question = new JLabel(number + ". " + quiz.getAnswer().eng + "의 뜻은 무엇일까요?");
			question.setHorizontalAlignment(SwingConstants.CENTER);
			question.setFont(new Font("바탕체", Font.BOLD, 24));
			
			if (!lastQuiz) {
				nextButtion = new JButton("다음");
			} else {
				nextButtion = new JButton("제출");
			}
			
			List<Word> choiceList = new ArrayList<>(quiz.choiceSet);
			Collections.shuffle(choiceList);
			for (int i=0; i<choiceList.size(); i++) {
				radioButions[i] = new JRadioButton(choiceList.get(i).kor);
				radioButions[i].setActionCommand(choiceList.get(i).kor);
				group.add(radioButions[i]);
				south.add(radioButions[i]);
			}

			this.add(nextButtion, BorderLayout.EAST);
			this.add(south, BorderLayout.SOUTH);
			this.add(question, BorderLayout.CENTER);
			
			nextButtion.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						if (nextButtion.getText().equals("다음")) {
							quizPanel.test.addChoiceAnswer(quiz, group.getSelection().getActionCommand());
							quizPanel.card.next(QuizTestPanel.this.getParent());
						}
						
						else {
							quizPanel.test.addChoiceAnswer(quiz, group.getSelection().getActionCommand());
							quizPanel.test.timer.interrupt();
							quizPanel.test.grade();
							quizPanel.result.init();
							quizPanel.card.next(QuizTestPanel.this.getParent());
						}
					} catch (NullPointerException ex) {
						JOptionPane.showMessageDialog(null, "정답을 선택해주세요!", "Message", JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
				
			});
		}
	}
	
	private class QuizResultPanel extends JPanel {
		JPanel center = new JPanel();
		JLabel scoreLabel, secondLabel;
		JButton okButton;
		
		public QuizResultPanel() {}
		
		public void init() {
			this.setLayout(new BorderLayout());
			
			scoreLabel = new JLabel("점수 : " + test.getScore(), SwingConstants.CENTER);
			scoreLabel.setFont(new Font("바탕체", Font.BOLD, 24));
			secondLabel = new JLabel("걸린 시간 : " + test.getSecond() + "초", SwingConstants.CENTER);
			secondLabel.setFont(new Font("바탕체", Font.BOLD, 24));
			
			center.add(scoreLabel);
			center.add(secondLabel);
			
			okButton = new JButton("확인");
			
			this.add(center, BorderLayout.CENTER);
			this.add(okButton, BorderLayout.SOUTH);
			
			okButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					JTabbedPane tp = (JTabbedPane) QuizPanel.this.getParent();
					tp.setEnabledAt(0, true);
					tp.setEnabledAt(1, true);
					
					resetPanels();
				}
				
			});
		}
	}
}
