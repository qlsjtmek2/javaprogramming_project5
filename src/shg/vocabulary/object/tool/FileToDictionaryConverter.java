package shg.vocabulary.object.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

import shg.vocabulary.exception.OpenFileException;
import shg.vocabulary.object.Dictionary;
import shg.vocabulary.object.EnglishVoca;
import shg.vocabulary.object.Word;

public class FileToDictionaryConverter extends Converter<File, Dictionary> {
	public FileToDictionaryConverter(File file) {
		super(file);
	}
	
	public Dictionary convert() {
		List<Word> voc = new ArrayList<>();
		
		try {
			FileInputStream fi = new FileInputStream(input);
			EncodeFinder encode = new EncodeFinder(input);
			Scanner scan = new Scanner(fi, encode.find());
			
			while (scan.hasNextLine()) {
				String str = scan.nextLine();
				String[] temp = str.split("\t");
				if (temp.length == 2)
					voc.add(new Word(temp[0].trim(),temp[1].trim()));
				else
					voc.add(new Word(temp[0].trim(),temp[1].trim(), Integer.parseInt(temp[2].trim())));
			}
			
			EnglishVoca voca = new EnglishVoca.EnglishVocaBuilder()
					.setVoc(voc)
					.setDictionaryName(input.getName())
					.setFilePath(input.getPath())
					.build();
			
			scan.close();
			return voca;
		} catch(ArrayIndexOutOfBoundsException | NumberFormatException | NullPointerException e) {
			throw new OpenFileException("fail open dictionary");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Message", JOptionPane.WARNING_MESSAGE);
			return null;
		}
	}
}
