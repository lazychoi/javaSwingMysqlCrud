package customerManage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class customerApp {

	private JFrame frame;
	private JTextField searchTextField;
	private DefaultTableModel tableModel;  // 테이블 데이터 편집하기 위해 필요
	private JTable table;
	private JTextField idLabel;
	private JTextField telLabel;
	private JTextField addresLabel;
	private JTextField nameLabel;
	private JTextField gradeLabel;
	private JTextField idTextfield;
	private JTextField telTextfield;
	private JTextField gradeTextfield;
	private JTextField nameTextfield;
	private JTextField addressTextfield;
	private JLabel lblNewLabel_1;
	private JButton btnSave;
	private JButton btnCancel;
	private JPanel panelTop;
	private JPanel panelBottom;
	private JPanel tablePanel;
	private Customer customer;
	private String[][] data;

	private JButton btnInsert;
	private JButton btnDelete;
	private JButton btnUpdate; 
	
	private int selectedIdx;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {    // ?? invokeLater 찾기
			public void run() {
				try {
					customerApp window = new customerApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}



	/**
	 * Create the application.
	 */
	public customerApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		customer = new Customer();

		// 패널 배치
		frame = new JFrame("고객 관리");    						// 메인 프레임
		frame.setSize(1024, 768);								// 프레임 크기
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// 종료 버튼 누르면 프로그램 종료
		frame.setLocationRelativeTo(null);						// 프레임을 화면 중앙에 배치
		frame.setResizable(false);								// 프레임 크기를 변경하지 못하도록 설정
		frame.getContentPane().setLayout(null);					// 프레임에 추가되는 컴포넌트 레이아웃 -> Absolute

		panelTop = new JPanel();								// 상단 패널
		panelTop.setBounds(6, 6, 1012, 354);					// 패널 위치와 크기  -> (x좌표, y좌표, 넓이, 높이)
		panelTop.setLayout(null);								// 상단 패널에 추가되는 컴포넌트 레이아웃 -> Absolute
		frame.getContentPane().add(panelTop);					// 프레임에 추가하기
		panelTop.setVisible(true);								// 패널 보이기

		panelBottom = new JPanel();								// 하단 패널
		panelBottom.setBounds(6, 372, 1012, 354);				// 패널 위치와 크기  -> (x좌표, y좌표, 넓이, 높이)
		panelBottom.setLayout(null);							// 하단 패널에 추가되는 컴포넌트 레이아웃 -> Absolute
		frame.getContentPane().add(panelBottom);				// 프레임에 추가하기
		panelBottom.setVisible(false);  						// 하단 패널 가리기

		tablePanel = new JPanel();								// 테이블 패널 생성
		tablePanel.setBounds(20, 50, 972, 268);					// 테이블 패널 위치와 크기
		frame.getContentPane().add(tablePanel);					// 테이블 패널 추가


		// 검색 필드
		JButton btnSearch = new JButton("검색어 입력"); // 검색어 입력 레이블을 버튼으로 구현(버튼 모양이 레이블보다 예뻐서)
		btnSearch.setBounds(6, 10, 129, 30);         // 검색어 입력 레이블 위치와 크기 -> (x좌표, y좌표, 넓이, 높이)
		panelTop.add(btnSearch);					 // 상단 패널에 붙이기

		searchTextField = new JTextField();   		 // 검색어 입력 텍스트필드 생성
		searchTextField.setBounds(135, 9, 857, 30);	 // 검색어 입력 텍스트필드 위치와 크기
		searchTextField.setColumns(10);				 // 검색어 길이 설정 
		panelTop.add(searchTextField);				 // 상단 패널에 붙이기
		
		searchTextField.addKeyListener(new KeyAdapter() {    // 검색어 입력 텍스트필드 이벤트
			public void keyReleased(KeyEvent e) {
				String val = searchTextField.getText();		 
				TableRowSorter<TableModel> trs = new TableRowSorter<>(table.getModel());
				table.setRowSorter(trs);
				trs.setRowFilter(RowFilter.regexFilter(val));
			}
		});

		// 버튼 배치

		btnInsert = new JButton("추가");
		btnInsert.setBounds(689, 318, 100, 30);
		panelTop.add(btnInsert);

		btnUpdate = new JButton("변경");
		btnUpdate.setBounds(792, 318, 100, 30);
		panelTop.add(btnUpdate);

		btnDelete = new JButton("삭제");
		btnDelete.setBounds(892, 318, 100, 30);
		panelTop.add(btnDelete);


		JLabel lblNewLabel = new JLabel(" 상세 정보");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblNewLabel.setBounds(20, 10, 100, 30);
		panelBottom.add(lblNewLabel);

		idLabel = new JTextField();
		idLabel.setEditable(false);
		idLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		idLabel.setText("       고 객 번 호");
		idLabel.setBounds(20, 50, 130, 40);
		panelBottom.add(idLabel);
		idLabel.setColumns(10);

		telLabel = new JTextField();
		telLabel.setEditable(false);
		telLabel.setText("       연   락   처");
		telLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		telLabel.setColumns(10);
		telLabel.setBounds(20, 100, 130, 40);
		panelBottom.add(telLabel);

		addresLabel = new JTextField();
		addresLabel.setEditable(false);
		addresLabel.setText("       주         소");
		addresLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		addresLabel.setColumns(10);
		addresLabel.setBounds(20, 150, 130, 40);
		panelBottom.add(addresLabel);

		nameLabel = new JTextField();
		nameLabel.setEditable(false);
		nameLabel.setText("       이            름");
		nameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		nameLabel.setColumns(10);
		nameLabel.setBounds(513, 50, 130, 40);
		panelBottom.add(nameLabel);

		gradeLabel = new JTextField();
		gradeLabel.setEditable(false);
		gradeLabel.setText("       회  원  등  급");
		gradeLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		gradeLabel.setColumns(10);
		gradeLabel.setBounds(513, 100, 130, 40);
		panelBottom.add(gradeLabel);

		idTextfield = new JTextField();
		idTextfield.setBackground(new Color(228, 229, 228));
		idTextfield.setEditable(false);
		idTextfield.setBounds(155, 50, 340, 40);
		panelBottom.add(idTextfield);
		idTextfield.setColumns(10);

		telTextfield = new JTextField();
		telTextfield.setColumns(10);
		telTextfield.setBounds(155, 100, 341, 40);
		panelBottom.add(telTextfield);

		gradeTextfield = new JTextField();
		gradeTextfield.setColumns(10);
		gradeTextfield.setBounds(651, 100, 341, 40);
		panelBottom.add(gradeTextfield);

		nameTextfield = new JTextField();
		nameTextfield.setColumns(10);
		nameTextfield.setBounds(651, 50, 341, 40);
		panelBottom.add(nameTextfield);

		addressTextfield = new JTextField();
		addressTextfield.setColumns(10);
		addressTextfield.setBounds(155, 150, 837, 40);
		panelBottom.add(addressTextfield);

		lblNewLabel_1 = new JLabel(" 특이 사항");
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblNewLabel_1.setBounds(20, 200, 100, 30);
		panelBottom.add(lblNewLabel_1);

		JTextArea textArea = new JTextArea();
		textArea.setBounds(20, 230, 972, 100);
		panelBottom.add(textArea);

		btnSave = new JButton("저장");
		btnSave.setBounds(892, 13, 100, 30);
		panelBottom.add(btnSave);

		btnCancel = new JButton("취소");
		btnCancel.setBounds(792, 12, 100, 30);
		panelBottom.add(btnCancel);

		// 이벤트 
		showTable();
		addTableRow();
		deleteTableRow();
		updateTableRow();
		saveData();
		cancelDate();

	}

	/** UI테이블 설정
	 * 
	 */

	public void showTable() {
		data = customer.getCustomers();
		//		for(String[] item:data)
		//			for(String ii: item)
		//				System.out.println(ii);
		String[] header = new String[]{"고객번호", "이름", "전화번호", "주소", "회원등급"};

		tableModel = new DefaultTableModel(data, header);  
		table = new JTable(tableModel) {                    // 셀에서 편집할 수 없게 함.
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setBounds(50, 50, 975, 255);

		// 셀 값 가운데 정렬
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );
		table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
		table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
		table.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
		table.getColumnModel().getColumn(4).setCellRenderer( centerRenderer );
		TableCellRenderer rendererFromHeader = table.getTableHeader().getDefaultRenderer();
		JLabel headerLabel = (JLabel) rendererFromHeader;
		headerLabel.setHorizontalAlignment(JLabel.CENTER);
		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  //행을 하나만 선택할 수 있도록

		// 컬럼 크기
		TableColumnModel colModel = table.getColumnModel();
		colModel.getColumn(0).setPreferredWidth(15);
		colModel.getColumn(1).setPreferredWidth(30);
		colModel.getColumn(2).setPreferredWidth(100);
		colModel.getColumn(3).setPreferredWidth(300);

		table.getTableHeader().setFont(new Font("NanumGothic", Font.BOLD, 13));  // header 폰트 설정
		table.getTableHeader().setPreferredSize(new Dimension(100, 30));		// header 넓이, 높이 
		table.setFont(new Font("NanumGothic", Font.PLAIN, 13));                 // 셀 폰트, 크기
		table.setRowHeight(30);
//		table.setAlignmentX(0);
		
		// 테이블에 스크롤바 추가
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(975, 255));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));	// padding(상, 좌, 하, 우)
		tablePanel.add(scrollPane); // JScrollPane을 panelTop에 바로 올리면 안 보임. 전용 tablePanel에 올려야 보임

	}

	/**
	 *  UI테이블에 행 추가
	 */
	public void addTableRow() {
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelBottom.setVisible(true);
			}
		});
	}

	/**
	 *  UI테이블에서 선택한 행의 고객번호(id) 가져오기 
	 */
	public String getCustomerId(int seledtedIdx) {
		String selectedId = (String) table.getValueAt(seledtedIdx, 0);  // 인덱스 행의 첫번째 컬럼값 반환
		return selectedId;
	}

	/**
	 *  UI테이블에서 선택한 행 삭제 
	 */
	public void deleteTableRow() {

		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				selectedIdx = table.getSelectedRow();    // 선택한 행 인덱스 반환. 선택한 행이 없으면 -1 반환 
				if( selectedIdx == -1) {   
					JOptionPane.showMessageDialog(null, "테이블에서 삭제할 행을 선택하세요.");
				} else {
					// 선택한 행의 고객번호(id) 가져오기
					String selectedId = getCustomerId(selectedIdx); 
					// db 데이터 삭제
					customer.deleteCustomer(selectedId);
					// UI 테이블 행 삭제
					tableModel.removeRow(selectedIdx);
					
					JOptionPane.showMessageDialog(null, "고객 정보가 삭제되었습니다");
				}
			}
		});
	}

	/**
	 *  UI테이블 행 변경
	 */

	public void updateTableRow() {
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedIdx = table.getSelectedRow();    // 선택한 행이 없으면 -1 반환 
				if( selectedIdx == -1) {   
					JOptionPane.showMessageDialog(null, "테이블에서 변경할 행을 선택하세요.");
				} else {
					idTextfield.setText((String)table.getValueAt(selectedIdx, 0));
					nameTextfield.setText((String)table.getValueAt(selectedIdx, 1));
					telTextfield.setText((String)table.getValueAt(selectedIdx, 2));
					addressTextfield.setText((String)table.getValueAt(selectedIdx, 3));
					gradeTextfield.setText((String)table.getValueAt(selectedIdx, 4));
					
					panelBottom.setVisible(true);

					// 테이블에서 다른 곳을 선택할 수 없도록 설정 
					
				}
			}
		});
	}


	/**
	 * 텍스트 필드에 값을 입력했는지 여부 검사
	 */
	public boolean isValid(String[] inputList) {

		// 필수 입력값이 비어있으면 안내
		if(inputList[0].equals("") | inputList[0].length() == 0) {
			nameTextfield.setText("고객 이름을 입력하세요.");
		} else if(inputList[1].equals("") | inputList[1].length() == 0) {
			telTextfield.setText("고객 전화번호를 입력하세요.");
		} else if(inputList[2].equals("") | inputList[2].length() == 0) {
			addressTextfield.setText("고객 주소를 입력하세요.");
		} else if(inputList[3].equals("") | inputList[3].length() == 0) {
			gradeTextfield.setText("고객 등급을 입력하세요.");
		}

		// 필수 입력칸이 입력되었는지 검사
		for(String item: inputList) {
			if(item.equals("") | item.length() == 0)
				return false;
		}
		return true;
	}

	/**
	 * UI테이블에 저장
	 */
	public void saveData() {
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String[] inputList = new String[4];
				inputList[0] = nameTextfield.getText();
				inputList[1] = telTextfield.getText();
				inputList[2] = addressTextfield.getText();
				inputList[3] = gradeTextfield.getText();

				boolean valid = isValid(inputList);  // 데이터가 모두 입력되었는지 검사  

				// 데이터베이스에 추가 (고객번호 필드는 비어있고 다른 필드는 모두 값이 입력됨)
				if (valid && idTextfield.getText().equals("")) {
					
					// 데이터베이스에 고객정보 추가하고 id 가져오기
					int id = customer.createCustomer(inputList[0], inputList[1], inputList[2], inputList[3]);

					if (id != -1) {
						String[] newRow = { Integer.toString(id), inputList[0], inputList[1], inputList[2], inputList[3] };
						
						tableModel.addRow(newRow);			// UI 테이블에 행 추가

						JOptionPane.showMessageDialog(null, "고객 정보가 추가되었습니다");

						tableModel.fireTableDataChanged();  // UI 테이블 정보 갱신
					}
					panelBottom.setVisible(false);          // 하단 패널 가리기
				
					// 데이터베이스 변경 (고객번호 필드에 값이 있으면 변경 처리)
				} else if (!idTextfield.getText().equals("")) {
					// db update
					customer.updateCustomer(idTextfield.getText(), inputList[0], inputList[1], inputList[2], inputList[3]);
					
					//UI table update. 고객번호 유지
					table.setValueAt(inputList[0], selectedIdx, 1);   
					table.setValueAt(inputList[1], selectedIdx, 2);
					table.setValueAt(inputList[2], selectedIdx, 3);
					table.setValueAt(inputList[3], selectedIdx, 4);
					
					JOptionPane.showMessageDialog(null, "고객 정보가 변경되었습니다");

					tableModel.fireTableDataChanged();  // UI 테이블 정보 갱신
					panelBottom.setVisible(false);
				}
			}
		});
	}

	/** 
	 * 취소 버튼 설정
	 */
	public void cancelDate() {
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nameTextfield.setText(null);
				telTextfield.setText(null);
				addressTextfield.setText(null);
				gradeTextfield.setText(null);
				panelBottom.setVisible(false);
			}
		});
	}

}
