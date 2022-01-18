package shg.vocabulary.object.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.mozilla.universalchardet.UniversalDetector;

/*
 * 파일 인코딩을 인식하는 객체
 * 출처: https://erim1005.tistory.com/entry/Java로-파일-인코딩-확인하기
 */

public class EncodeFinder {
	private File file;
	
	public EncodeFinder(File file) {
		super();
		this.file = file;
	}

	public String find() throws IOException {
	    byte[] buf = new byte[4096];
		FileInputStream fis = new FileInputStream(file);
	    UniversalDetector detector = new UniversalDetector(null);
	 
	    int nread;
	    while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
	      detector.handleData(buf, 0, nread);
	    }
	    
	    detector.dataEnd();
	    String encoding = detector.getDetectedCharset();
	    detector.reset();
	    
	    fis.close();
	    
	    return encoding;
	}
}
