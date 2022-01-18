package shg.vocabulary.object.tool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import shg.vocabulary.object.Dictionary;
import shg.vocabulary.object.Word;

public class DictionaryToFileConverter extends Converter<Dictionary, File> {
	private final String pathName;

    /**
     * 단어장 객체를 파일 객체로 변환해주는 컨버터 입니다.
     *
     * @param dictionary 단어장 객체를 넣으세요. ex) EnglishVoca,
     * 			현재 열려있는 단어장은 <code>OpenedDictionary.getInstance()</code>
     * 			메소드로 얻을 수 있음.
     * @param pathName  저장될 파일 경로를 입력하세요. 파일 명 뒤에 <code>.txt</code>를 붙이지 않아도
     * 					자동으로 붙여줍니다. 붙여도 무방함
     */
	public DictionaryToFileConverter(Dictionary dictionary, String pathName) {
		super(dictionary);
		this.input = dictionary;
		if (!pathName.contains(".txt"))
			pathName = pathName + ".txt";
		this.pathName = pathName;
	}
	
    /**
     * 단어장을 파일로 변환합니다.
     * <code>convert()</code>만 해도 지정한 경로에 파일이 저장됨.
     * 
     * @return 변환된 파일 객체를 리턴함.
     */
	public File convert() {
		File file = new File(pathName);
		List<Word> voc = input.getVoc();
		
		try {
			file.createNewFile();
		    FileWriter fw = new FileWriter(file);
		    BufferedWriter bw = new BufferedWriter(fw);
		    PrintWriter pw = new PrintWriter(bw);
		    for (Word word : voc) {
		    	pw.println(word);
			}
		    
		    pw.flush();
		    pw.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		return file;
	}
}
