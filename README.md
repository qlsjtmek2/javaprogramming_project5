# javaprogramming_project5
건국대학교 JAVA 프로그래밍 단어장 프로젝트  

기획.txt  

TIP. 제작할 때 이런식으로 어떤 스레드로 구성할건지도 기획하는게 좋을 듯  
https://jungeun960.tistory.com/21  
기획할 때 고려할 요소 : 구현할 기능들, 필요한 객체 클래스, 클래스 다이어그램, 프로그램 흐름(플로우 다이어그램), 스레드 등  
기능 조직화 (UI, 이벤트, Entity, 통신, File 등)  

객체는 어떤 행위를 하는 대상  
객체 생성할 때 빌더 패턴 사용  
하나의 객체는 오직 하나의 책임(변경)을 가져야 한다  
변경 가능성이 없는 객체는 변수에 final을 붙이자  
기능을 달아줄 땐 인터페이스를 만들어서 달아주기  
굳이 변수를 다 private로 만들어서 게터세터 만들 필요는 없고,  
특별하고 명확한 목적이 있을때 접근제한자를 사용해주는게 좋음  
빌더패턴: https://readystory.tistory.com/121  

객체지향 5원칙  
1. 객체는 오직 하나의 책임을 가져야 한다. 
2. 객체는 확장에 대해서는 개방적이고 수정에 대해서는 폐쇄적이어야 한다는 원칙이다. 
즉, 객체 기능의 확장을 허용하고 스스로의 변경은 피해야 한다.  
3. 자식 클래스는 언제나 자신의 부모 클래스를 대체할 수 있다는 원칙이다.  
즉 부모 클래스가 들어갈 자리에 자식 클래스를 넣어도 계획대로 잘 작동해야 한다는 것.  
4. 범용 인터페이스 하나보다는 특정 클라이언트를 위한 여러 개의 인터페이스 분리가 더 좋다.  
5. 프로그래머는 구체화가 아니라 추상화에 의존해야 한다고 한다.   
즉 구현 클래스(구현체)가 아니라 인터페이스(역할)에 의존하라는 이야기이다.  
(연극을 예로 들면 배역(인터페이스)과 배우(구현체)를 예로 들 수 있다. 이 때 연극은   
특정 배우를 염두에 두고 기획되기보다 배역에 집중해서 기획되어야 한다.)  
  
클래스 다이어그램  
https://gmlwjd9405.github.io/2018/07/04/class-diagram.html  



[ 구현할 기능들 ]  

0. 구현할 기능들 O  
1. 필요한 객체와 객체가 맡는 기능 구상 O  
2. GUI 구성 (본인 사진 및 학번 이름이 포함된 메인 화면 필수) O  
//3. 기능 조직화 (UI는 UI관련 패키지에서 분담하고, 이벤트는 이벤트 관련 패키지에서 분담하도록.  
		단일 기능 전문화가 명확하도록 설계)   
// 4. 클래스 다이어그램 대강 그리기 (대충 그리고 코드짜면서 미흡하거나 추가할부분 수정해가면 됨)  
5. 프로그램 흐름 대강 설계 (플로우 다이어그램) O  
// 6. 필요한 쓰레드 정리  
7. GUI 구현  
	메뉴 구성 O  
	홈화면 구성 O  
	단어장화면 구성 O  
	퀴즈화면 구성 O  
8. word.txt -> 다이얼로그로 단어장 파일을 선택하도록 변경 O  
	열기 눌르면 파일다이얼로그 뜸 O  
	정상적으로 열었다면 O  
		FileToDictionaryConverter 객체 생성 O  
		컨버트해서 반환된 단어장을 싱글톤에 저장 O  

9. 싱글톤에 단어장이 비어있다면 tabpane에서 단어장, 퀴즈 안보이게, 안비어있다면 보이게 하기 O  

	MyTabbedPane 생성부분에 addListener {  
		getInstance가 null이면  
			1, 2 false  
		아니면  
			1, 2 true  
	}  
	연결해야함O  
	
	setInstance 메소드가 실행되면 이벤트 실행O  
		
10. 빈 단어장 생성 기능 O  
	저장할 위치 경로를 설정 O  
	비어있는 단어장을 새로 만들고 O  
	DictionaryToFileConverter 객체 생성 O  
	컨버트O  
	빈 단어장을 싱글톤에 저장O  

DictionaryToFileConverter 객체
	dictionary, pathName를 인자로 받아옴 O
	단어장에 단어리스트를 가져와서O
	파일로 eng + \tab + kor 형식으로 한줄한줄 써내려가고O
	파일 반환O
	

11. 단어리스트 출력 기능O
- 등록되어 있는 모든 영어단어와 뜻을 화면에 출력하는 기능
- VocaPanel에 있는 wordTable를 수정해야함
	VocaPanel에 OpenedChangeListener 추가O
		PrintToGUI 객체 생성O
		현재 열려있는 단어장의 단어목록들을 가져와서O
		print(List<Word>)O

12. 단어 뜻 검색 기능O
	VocaPanel에서 검색 버튼, 텍스트필드 엔터 입력받았을때 이벤트 생성O
		현재 열려있는 단어장 불러오고O
		단어 검색해서 찾으면 리스트에 하나씩 추가O
		PrintToGUI 객체 생성O
		print(List<Word>)O
+ 원래대로 돌아오는 기능도 있어야함O

13. 단어 추가 기능O
	새로운 영어 단어와 뜻을 추가하는 기능
	단어추가 버튼 누르면O
		텍스트 필드 두개 입력받는 다이얼로그 띄우기O
		필드 둘다 잘 입력되어있고 확인버튼 눌렀으면O
			두개의 String 값 반환O
			현재 열려있는 단어장 불러오고O
			dic.addWordO
			싱글톤에 추가된 단어장 업데이트O
			PrintToGUI 객체 생성O
			print(List<Word>)O

단어 추가할때 단어 입력받는 모달 다이얼로그 생성하기O

14. 저장 기능 추가O
	현재 열려있는 단어장 가져와서O
	DictionaryToFileConverter 객체 생성O
	컨버트O

15. 다른 이름으로 저장 기능 추가O
	저장할 위치 경로를 설정O
	DictionaryToFileConverter 객체 생성O
	컨버트O

16. 객관식 퀴즈 기능O
- 라디오 버튼을 이용해서 객관식 1~4번을 선택하도록
- 퀴즈중엔 탭 못바꾸게하기
	MakeQuiz 객체 생성O
	문제 10개 만들기O
	QuizTest 객체 생성해서 만든 퀴즈 10개 인자로 넘기기O
	PrintToGUI 객체 생성O
	quiz startO
	QuizPanel 내부변수에 quizTest 저장O

PrintToGUI
	시험지와 QuizPanel을 인자로 받아서 시험을 실제로 출력하는 기능 제작
	퀴즈 테스트 패널 개수만큼 제작 (new QuizTestPanel(Quiz quiz))O
	panels에 시험 개수만큼 시험 패널 add하기O
	Home, 단어장 disableO
	타이머 쓰레드 생성O
	타이머 쓰레드 시작O

QuizTestPanel
	보더 레이아웃으로 설정O
	JPanel south (Flow Layout)O
	라디오버튼 4개O
	버튼O
	JlabelO
	
	
QuizPanel
	다음 버튼 눌리면O
		라디오 버튼이 눌려있으면
			체크한 답 기록하고O
			cardlayout nextO
		아니면O
			답을 선택하세요! 팝업창O
	제출 버튼 눌리면
		타이머 쓰레드 종료O
		타임 가져와서 quizTest에 기입O
		몇개 맞았는지 채점해서 quizTest에 기입O
			foreach quizList 돌려서 하나하나 답비교O
			오답인건 오답 카운트에 추가O
				
	확인 버튼 눌리면
		Home, 단어장 enableO
		resetPanels()O
		test = null;O

 
17. 오답노트 기능O
	퀴즈에서 자주 틀린 단어 20개를 빈도수가 큰 순서대로 출력해주기
	현재 열려있는 단어장 불러오기O
	오답노트 체크박스를 체크했다면O
		getFilterWords로 많이틀린 단어 20개 리스트 가져오기O
		PrintToGUI 객체 생성O
		print(List<Word>)O
	체크를 풀었다면O
		PrintToGUI 객체 생성O
		print(List<Word>)O

18. 단어장에 단어 부족하면 퀴즈 출제 불가능하도록 예외처리O
19. 열려있는 단어장이 없다면 저장 못하게 하기O
19. 컨트롤 S 누르면 저장하기O
21. 단어수가 적을때 문제가 하나만 나오는거 수정O
22. 다맞췄을때만 9점나오는 거 수정O
21. 과제5.pdf 기준에 다 맞췄는지 체크
	올바른 형식의 txt 파일 오픈했는지 예외처리 해야하고O
	표에 틀린 개수도 뜨도록 개선O
	내 사진 넣어야함



[ 필요한 객체들 ]
- event
OpenedChangeListener (열려있는 단어장이 바뀌었을 때 발동하는 이벤트) O

- object

dictionary 사전(추상) O
	사전이 갖고있어야 하는건 뭐지?
	단어리스트
	사전의 이름

영어 단어장O
	단어들 리스트
	단어 추가 기능 (Addable)O
	단어 검색 기능 (Searchable)O
	많이 틀린거 20개 반환하는 기능 (인자로 람다표현식) (Sortable)O

현재 펼치고있는 단어장 저장하는 싱글톤 (OpenedDictionary) O

단어 O
	영단어와 뜻 정보
	퀴즈에서 출제되었을 때 몇번 틀렸는지 정보를 담고 있음

시험지 객체(QuizTest) O
	n개의 시험문제를 담고있음
	최종점수
	걸린시간
	채점하기 기능
	set걸린시간 메소드 
	타이머 쓰레드 내부클래스
	
시험문제 객체(Quiz) : 퀴즈 하나O
	선지들의 집합
	답
	정답 여부 정보를 담고있음

TableColumn 객체
	column 이름과
	PreferredWidth 숫자
	value(word, table) 함수정보를 담고있는 변수 (함수형인터페이스)
TableColumn 객체를 잘 만들어서 VocaPanel 안에 columns 리스트에 추가만 하면
표룰 수정할 수 있도록 하기위함

- object-tool
PrintToGUI O
: 단어장 내용을 GUI로 출력하는 역할을 맡는 객체
	단어장의 단어 목록을 인자로 받아온 Table 객체에 보여주는 기능O
	시험지와 QuizPanel을 인자로 받아서 시험을 실제로 출력하는 기능
	
Converter<T,V>(추상)O
	컨버터 추상 클래스
FileTo단어장Converter O
	txt 파일을 단어장 객체로 만들어서 반환하는 기능O
단어장ToFileConverterO
	단어장을 파일로 변환해서 반환하는 기능O

단어장에서 시험문제 출제하는 객체 (MakeQuiz)O
	단어장에서 단어를 뽑아 랜덤으로 출제하는 기능O
		단어장 필드에 갖고있음O
		Quiz make(퀴즈의 선지 개수)O


- ui
프레임 객체
TabPanel (JTabbedPane)
	홈, 단어장, 퀴즈 세개 패널 정보를 가짐
Menu
	파일, 도움말
HomePanel (GridLayout)
	오른쪽 FlowLayout Panel
	내 이미지 사진과 학번이름 정보 담고있음
VocaPanel (BorderLayout)
	검색 textField
	검색 버튼
	단어추가 버튼
	오답노트 체크박스
	단어 리스트 표+스크롤바
	North Panel (FLow Layout)
QuizPanel (CartLayout)
	List<JPanel>
	4개의 라디오버튼
	문제 Label
	다음 버튼
	제출 버튼
	확인 버튼
	점수, 시간 Label


[ UI 구상 ]
메인화면
메뉴
	File
	ㄴ New
	ㄴ Open
	ㅡㅡㅡㅡㅡ
	ㄴ Save
	ㄴ Save as

단어리스트 출력 패널 (진짜 단어장처럼 구성)
	위에 검색할 수 있는 그 textline 하나 만들고 검색하면
	아래 쭉 있던 표 대신 검색결과 나오도록
	(정확히 일치하는것만 하지말고 부분적인 검색도 허용하자)
	그리고 옆에 + 버튼 하나 만들어서 그 버튼을 누르면
	다이얼로그 창 띄우면서 단어 추가할 수 있도록 ㅇㅇ
	오답노트 체크박스 (툴팁으로 시험 오답률 높은순 20개를 출력합니다. 띄워주기)
	

퀴즈 패널
	퀴즈 시작 버튼 존재
	카드 레이아웃으로 만들어서 문제 넘기기
	시험지 출제할때 문제 몇개 출제할건지 객체에 입력받으면
	동적으로 문제 개수에 맞게 패널 생성(사실상 10개 고정, 총 12개 패널)
	후 점수, 몇초 걸렸는지 패널 추가
		-> 여기서 확인버튼 누르면 홈 패널로 돌아가고
		홈 패널을 제외한 나머지 패널 전부 삭제


[ 클래스 다이어그램 ]


[ 플로우 다이어그램 ]


[ 필요한 쓰레드들 ]
main Thread

[ 이벤트 리스너 종류 ]
OpenedChangeListener - 열려있는 단어장이 바뀌었을 때 발동하는 이벤트


