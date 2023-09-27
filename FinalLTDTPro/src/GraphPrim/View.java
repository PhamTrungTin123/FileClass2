package GraphPrim;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class View extends JFrame {

	List<Integer> point = new ArrayList<>();
	List<DrawGraph> listVertex = new ArrayList<>();
	int countVertex = 0;
	int[][] matrixWeight;

	// public int[][] getMatrixWeight() {
	// return matrixWeight;
	// }

	// public void setMatrixWeight(int[][] matrixWeight) {
	// this.matrixWeight = matrixWeight;
	// }

	String mouseClick;
	Control control;

	DrawGraph dpw1;
	DrawGraph dpw2;

	JPanel drawPanel;

	// JTextField startVertexTextField;
	// JTextField endVertexTextField;
	JTextField weightTextField;

	JComboBox<String> startVertexComboBox;
	JComboBox<String> endVertexComboBox;

	JTextArea resultTextArea;
	JTextArea matrixTextArea;

	JPanel topPanel, createGraphPanel, resultPanel;

	public  View() {
		this.setTitle("Đồ Án LTDT-Nhóm15-2023");
		this.setSize(1000, 900);
		this.setLayout(new BorderLayout());

		this.init();

		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		revalidate();
	}

	public void init() {

		topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1, 4));

		Border redBordered = BorderFactory.createLineBorder(Color.CYAN, 2);
		JPanel typeGraphPanel = new JPanel();
		typeGraphPanel.setBorder(BorderFactory.createTitledBorder(redBordered, "Loại Đồ Thị", TitledBorder.CENTER,
				TitledBorder.TOP, new Font("Tahoma", Font.BOLD, 13), Color.RED));
		typeGraphPanel.setLayout(new GridLayout(2, 1));

		JRadioButton rdbUnGraph = new JRadioButton("Đồ thị vô hướng");
		JRadioButton rdbDirGraph = new JRadioButton("Đồ thị có hướng");
		rdbUnGraph.setSelected(true);

		ButtonGroup btgTypeGraph = new ButtonGroup();
		btgTypeGraph.add(rdbUnGraph);
		btgTypeGraph.add(rdbDirGraph);

		typeGraphPanel.add(rdbUnGraph);
		typeGraphPanel.add(rdbDirGraph);

		// Khởi tạo mảng chứa giá trị của biến cờ

		JPanel algoPrimPanel = new JPanel();
		algoPrimPanel.setBorder(BorderFactory.createTitledBorder(redBordered, "Tìm cây khung nhỏ nhất",
				TitledBorder.CENTER, TitledBorder.TOP, new Font("Tahoma", Font.BOLD, 13), Color.RED));

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.CENTER;
		JButton btnFindMST = new JButton("Thuật toán prim");
		btnFindMST.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		algoPrimPanel.add(btnFindMST, c);

		JPanel checkConnectedPanel = new JPanel();
		checkConnectedPanel.setBorder(BorderFactory.createTitledBorder(redBordered, "Kiểm tra tính liên thông",
				TitledBorder.CENTER, TitledBorder.TOP, new Font("Tahoma", Font.BOLD, 13), Color.RED));
		btnFindMST.addActionListener(e -> {
			try {
				control = new Control(matrixWeight);
				int numConnectedComponents = control.countConnectedComponents(matrixWeight);
				// Đếm số lượng thành phần liên thông
				if (numConnectedComponents != 1) {
					// Nếu số lượng thành phần liên thông lớn hơn 1, thông báo cho người dùng
					// và không chạy thuật toán Prim
					JOptionPane.showMessageDialog(null, "Đồ thị không liên thông!");
					return;
				}
				String findMST = control.showMST(matrixWeight, rdbUnGraph.isSelected());
				resultTextArea.setText(findMST);
				matrixTextArea.setText(control.matrixToString(control.getMSTMatrix(matrixWeight)));

				// to mau do thi
				int[][] matrix = this.control.getMSTMatrix(matrixWeight);

				for (int i = 0; i < matrix.length; i++) {
					for (int j = 0; j < matrix.length; j++) {

						if (matrix[i][j] != 0) {

							int firstVex = i;
							int endVex = j;
							int weight = matrix[i][j];
							dpw1 = listVertex.get(i);
							dpw2 = listVertex.get(j);
							dpw1.setSetcolor(true);

							dpw1.setWeight(weight + "");
							dpw1.setDoublePoint(dpw2, dpw1);
							dpw1.isAdd = true;
							dpw2.isAdd = true;

							matrixWeight[firstVex][endVex] = weight;
							matrixWeight[endVex][firstVex] = weight;
							dpw1.drawLineWithoutArrow(getGraphics(), dpw1.x + 11, dpw1.y + 100, dpw2.x + 12,
									dpw2.y + 100,
									7, 7);

						}
					}
				}
			} catch (NullPointerException e1) {
				JOptionPane.showMessageDialog(null, "Hãy tạo đồ thị");
			}

		});

		JButton btnConnectedComponents = new JButton("Kiểm tra liên thông");
		btnConnectedComponents.addActionListener(e -> {
			try {
				control = new Control(matrixWeight);
				int count = control.countConnectedComponents(matrixWeight);
				String result = "";

				if (rdbUnGraph.isSelected()) {
					if (count == 1) {
						result = "Đồ thị liên thông.";
					} else {
						result = "Đồ thị không liên thông.";
					}
				} else {
					
					if (count == 1) {
						result = "Đồ thị liên thông mạnh.";
					} else {
						result = "Đồ thị liên thông yếu.";
					}
				}

				result += "\nSố thành phần liên thông: " + count;
				resultTextArea.setText(result);
				matrixTextArea.setText(control.matrixToString(matrixWeight));

			} catch (NullPointerException e1) {
				JOptionPane.showMessageDialog(null, "Hãy tạo đồ thị");
			}

		});

		checkConnectedPanel.add(btnConnectedComponents, c);
		checkConnectedPanel.add(Box.createVerticalStrut(10));
		topPanel.add(typeGraphPanel);
		topPanel.add(algoPrimPanel);
		topPanel.add(checkConnectedPanel);
		DefaultComboBoxModel<String> endVertexComboBoxModel = new DefaultComboBoxModel<>();
		JComboBox<String> endVertexComboBox = new JComboBox<>(endVertexComboBoxModel);
		DefaultComboBoxModel<String> startVertexComboBoxModel = new DefaultComboBoxModel<>();
		JComboBox<String> startVertexComboBox = new JComboBox<>(startVertexComboBoxModel);

		JPanel createGraphPanel = new JPanel();
		createGraphPanel.setLayout(new BorderLayout());

		drawPanel = new JPanel();
		drawPanel.setBackground(Color.white);
		drawPanel.addComponentListener(new ComponentAdapter() {

			public void componentResized(ComponentEvent e) {
				try {
					// Vẽ lại tất cả các đối tượng trong listdraw
					for (DrawGraph point : listVertex) {
						point.drawVertex(getGraphics());
					}

					// Lấy các thông tin cần thiết từ các text field và các đối tượng vẽ
					String dp1 = null;
					String dp2 = null;
					if (startVertexComboBox.getSelectedItem() != null) {
						dp1 = startVertexComboBox.getSelectedItem().toString();
					}
					if (endVertexComboBox.getSelectedItem() != null) {
						dp2 = endVertexComboBox.getSelectedItem().toString();
					}
					String weightInput = weightTextField.getText();
					int firstVex = Integer.parseInt(dp1);
					int endVex = Integer.parseInt(dp2);
					int weight = Integer.parseInt(weightInput);

					// Lưu trữ thông tin về cạnh mới được vẽ trong một mảng
					matrixWeight[firstVex - 1][endVex - 1] = weight;
					if (!rdbDirGraph.isSelected()) {
						matrixWeight[endVex - 1][firstVex - 1] = weight;
					}

					// Vẽ lại tất cả các cạnh trên đồ thị
					Graphics g = getGraphics();
					for (int i = 0; i < listVertex.size(); i++) {
						DrawGraph p1 = listVertex.get(i);
						for (int j = i + 1; j < listVertex.size(); j++) {
							DrawGraph p2 = listVertex.get(j);
							int w = matrixWeight[p1.p - 1][p2.p - 1];
							if (w > 0) {
								if (rdbDirGraph.isSelected()) {
									// g.setColor(Color.red);
									g.drawString(Integer.toString(w), (p1.x + p2.x + 10) / 2, (p1.y + p2.y) / 2 + 116);
									g.setColor(Color.black);
									p1.drawArrowLine(g, p1.x + 11, p1.y + 100, p2.x + 12, p2.y + 100, 5, 5);
								} else {
									// g.setColor(Color.red);
									g.drawString(Integer.toString(w), (p1.x + p2.x + 10) / 2, (p1.y + p2.y) / 2 + 116);
									g.setColor(Color.black);
									p1.drawLineWithoutArrow(g, p1.x + 11, p1.y + 100, p2.x + 12, p2.y + 100, 0, 0);
								}
							}
						}
					}
				} catch (NumberFormatException ex) {
					// Xử lý ngoại lệ khi nhập không đúng định dạng số
					// Không thiển thị gì
				}
			}
		}

		// DrawPoint pp = new DrawPoint(e.getX(), e.getY(), countPoint);
		// pp.paintComponent(getGraphics());
		);

		//

		JPanel functionPanel = new JPanel();
		functionPanel.setLayout(new BorderLayout());
		functionPanel.setBorder(BorderFactory.createTitledBorder(redBordered, "Chức năng", TitledBorder.CENTER,
				TitledBorder.TOP, new Font("Tahoma", Font.BOLD, 13), Color.RED));

		JPanel subFunctionPanel = new JPanel();
		subFunctionPanel.setLayout(new BorderLayout());

		//
		JPanel mainFunctionPanel = new JPanel();
		mainFunctionPanel.setLayout(new GridLayout(3, 2));

		// DefaultComboBoxModel<String> startVertexComboBoxModel = new
		// DefaultComboBoxModel<>();
		// JComboBox<String> startVertexComboBox = new
		// JComboBox<>(startVertexComboBoxModel);
		mainFunctionPanel.add(new JLabel("Đỉnh đầu: "));
		mainFunctionPanel.add(startVertexComboBox);

		// DefaultComboBoxModel<String> endVertexComboBoxModel = new
		// DefaultComboBoxModel<>();
		// JComboBox<String> endVertexComboBox = new
		// JComboBox<>(endVertexComboBoxModel);
		mainFunctionPanel.add(new JLabel("Đỉnh cuối: "));
		mainFunctionPanel.add(endVertexComboBox);

		// startVertexTextField = new JTextField();
		// endVertexTextField = new JTextField();

		JLabel weightLabel = new JLabel("Trọng số: ");
		weightTextField = new JTextField();
		// mainFunctionPanel.add(startVLabel);
		// mainFunctionPanel.add(startVertexTextField);
		// mainFunctionPanel.add(startVertexList);
		// mainFunctionPanel.add(endVLabel);
		// mainFunctionPanel.add(endVertexList);
		// mainFunctionPanel.add(endVertexTextField);
		mainFunctionPanel.add(weightLabel);
		mainFunctionPanel.add(weightTextField);

		JPanel mainButtonPanel = new JPanel();
		mainButtonPanel.setLayout(new GridLayout(5, 1));

		JButton btnDelE = new JButton("Xóa cạnh");
//		JButton btnDelV = new JButton("Xóa đỉnh");

		btnDelE.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {

					if (startVertexComboBox != null && endVertexComboBox != null) {
						String dp1 = startVertexComboBox.getSelectedItem().toString();
						String dp2 = endVertexComboBox.getSelectedItem().toString();

						for (int i = 0; i < listVertex.size(); i++) {
							String ppp = listVertex.get(i).p + "";
							if (ppp.equals(dp1))
								dpw1 = listVertex.get(i);
							if (ppp.equals(dp2))
								dpw2 = listVertex.get(i);

						}

						String weightInput = weightTextField.getText();

						int firstVex = Integer.parseInt(dp1);
						int endVex = Integer.parseInt(dp2);
						int weight = Integer.parseInt(weightInput);
						// vex = new ArrayList<Integer>();

						if (matrixWeight == null)
							matrixWeight = new int[countVertex][countVertex];

						dpw1.setWeight(weightInput);
						dpw1.setDoublePoint(dpw2, dpw1);
						dpw1.isAdd = true;
						if (rdbUnGraph.isSelected() == true) {

							matrixWeight[firstVex - 1][endVex - 1] = weight;
							matrixWeight[endVex - 1][firstVex - 1] = weight;
							dpw1.deleteEdge(getGraphics(), dpw1.x + 11, dpw1.y + 100, dpw2.x + 12, dpw2.y + 100, 0, 0);
							updateForDeleteEdge(matrixWeight, firstVex, endVex);
						} else {
							matrixWeight[firstVex - 1][endVex - 1] = weight;
							dpw1.eraseArrowLine(getGraphics(), dpw1.x + 11, dpw1.y + 100, dpw2.x + 12, dpw2.y + 100, 5,
									5);
							dpw1.deleteEdge(getGraphics(), dpw1.x + 11, dpw1.y + 100, dpw2.x + 12, dpw2.y + 100, 5, 5);
							updateForDeleteEdge(matrixWeight, weight, weight);
						}
					}

				} catch (NullPointerException exx) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, "Hãy tạo cạnh");
				}

			}

		
			private void updateForDeleteEdge(int[][] matrixWeight, int firstVex, int endVex) {
			    if (rdbDirGraph.isSelected()) {
			        matrixWeight[firstVex - 1][endVex - 1] = 0;
			    } else {
			        matrixWeight[firstVex - 1][endVex -1] = 0;
			        matrixWeight[endVex - 1][firstVex - 1] = 0;
			    }
			
		
			}
		});
		JButton btnAddE = new JButton("Thêm cạnh");

		btnAddE.addActionListener(e -> {
			try {

				if (countVertex == 0) {
					JOptionPane.showMessageDialog(null, "Hãy tạo đỉnh");

				}

				if (startVertexComboBox != null && endVertexComboBox != null) {
					Object startVertex = startVertexComboBox.getSelectedItem();
					Object endVertex = endVertexComboBox.getSelectedItem();
					if (startVertex != null && endVertex != null) {
						String dp1 = startVertex.toString();
						String dp2 = endVertex.toString();
						for (int i = 0; i < listVertex.size(); i++) {
							String ppp = listVertex.get(i).p + "";
							if (ppp.equals(dp1))
								dpw1 = listVertex.get(i);
							if (ppp.equals(dp2))
								dpw2 = listVertex.get(i);

						}

						String weightInput = weightTextField.getText();

						int firstVex = Integer.parseInt(dp1);
						int endVex = Integer.parseInt(dp2);
						int weight = Integer.parseInt(weightInput);
						// vex = new ArrayList<Integer>();

						if (matrixWeight == null)
							matrixWeight = new int[countVertex][countVertex];

						dpw1.setWeight(weightInput);
						dpw1.setDoublePoint(dpw2, dpw1);
						dpw1.isAdd = true;
						if (rdbUnGraph.isSelected() == true) {

							matrixWeight[firstVex - 1][endVex - 1] = weight;
							matrixWeight[endVex - 1][firstVex - 1] = weight;
							dpw1.drawLineWithoutArrow(getGraphics(), dpw1.x + 11, dpw1.y + 100, dpw2.x + 12,
									dpw2.y + 100,
									7,
									7);
						} else {
							matrixWeight[firstVex - 1][endVex - 1] = weight;
							dpw1.drawArrowLine(getGraphics(), dpw1.x + 11, dpw1.y + 100, dpw2.x + 12, dpw2.y + 100, 7,
									7);
						}
					}
				}

			} catch (NullPointerException | NumberFormatException edgeEr) {
				// TODO: handle exception
				// if (weightTextField.getText() == null) {

				JOptionPane.showMessageDialog(null, "Hãy thêm trọng số để tạo cạnh");
				// }
			}

		});
		rdbUnGraph.addActionListener(e -> {
			// repaint();

			try {
				JOptionPane.showMessageDialog(null, "Bạn đã chuyển qua đồ thị vô hướng");
				if (startVertexComboBox != null && endVertexComboBox != null) {
					String dp1 = startVertexComboBox.getSelectedItem().toString();
					String dp2 = endVertexComboBox.getSelectedItem().toString();
				}
				Graphics g = getGraphics();
				for (int i = 0; i < listVertex.size(); i++) {
					for (DrawGraph point : listVertex) {
						DrawGraph point1 = listVertex.get(i);
						for (int j = i + 1; j < listVertex.size(); j++) {
							DrawGraph point2 = listVertex.get(j);
							int w = matrixWeight[point1.p - 1][point2.p - 1];

							point1.eraseArrowLine(g, point1.x + 11, point1.y + 100, point2.x + 12, point2.y + 100, 5,
									5);
							// g.drawString(Integer.toString(w), (point1.x + point2.x + 10) / 2,
							// (point1.y + point2.y) / 2 + 116);
							g.setColor(Color.white);
							point1.drawLineWithoutArrow(g, point1.x + 11, point1.y + 100, point2.x + 12, point2.y + 100,
									0,
									0);

						}
					}
				}

			} catch (NullPointerException x) {
				// TODO: handle exception
			}

		});
		rdbDirGraph.addActionListener(e -> {
			try {

				JOptionPane.showMessageDialog(null, "Bạn đã chuyển qua đồ thị có hướng");

				// repaint();
				if (startVertexComboBox != null && endVertexComboBox != null) {
					String dp1 = startVertexComboBox.getSelectedItem().toString();
					String dp2 = endVertexComboBox.getSelectedItem().toString();

					for (int i = 0; i < listVertex.size(); i++) {
						String ppp = listVertex.get(i).p + "";
						if (ppp.equals(dp1))
							dpw1 = listVertex.get(i);
						if (ppp.equals(dp2))
							dpw2 = listVertex.get(i);

					}

					String weightInput = weightTextField.getText();

					int firstVex = Integer.parseInt(dp1);
					int endVex = Integer.parseInt(dp2);
					int weight = Integer.parseInt(weightInput);
					// vex = new ArrayList<Integer>();

					if (matrixWeight == null)
						matrixWeight = new int[countVertex][countVertex];

					dpw1.setWeight(weightInput);
					dpw1.setDoublePoint(dpw2, dpw1);
					dpw1.isAdd = true;

					matrixWeight[firstVex - 1][endVex - 1] = weight;
					dpw1.drawArrowLine(getGraphics(), dpw1.x + 11, dpw1.y + 100, dpw2.x + 12, dpw2.y + 100, 5, 5);
					System.out.println("UnGraph");

				}
			} catch (NullPointerException ex) {
				// TODO: handle exception
			}

		});

		JButton btnAddV = new JButton("Thêm đỉnh");
		btnAddV.addActionListener(e -> {
			drawPanel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					countVertex += 1;
					DrawGraph pp = new DrawGraph(e.getX(), e.getY(), countVertex);
					pp.drawVertex(getGraphics());
					point.add(pp.p);
					listVertex.add(pp);
					// Thêm đỉnh mới vào danh sách và cập nhật JComboBox
					String vertexLabel = String.valueOf(countVertex);
					startVertexComboBoxModel.addElement(vertexLabel);
					endVertexComboBoxModel.addElement(vertexLabel);
				}
			});
		});

		mainButtonPanel.add(btnAddV);
		mainButtonPanel.add(btnAddE);
//		mainButtonPanel.add(btnDelV);
		mainButtonPanel.add(btnDelE);

		subFunctionPanel.add(mainFunctionPanel, BorderLayout.CENTER);
		subFunctionPanel.add(mainButtonPanel, BorderLayout.SOUTH);

		JPanel additionalFunctionsPanel = new JPanel();
		additionalFunctionsPanel.setBackground(Color.LIGHT_GRAY);
		additionalFunctionsPanel.setLayout(new GridLayout(1, 3));

		JButton btnReadFile = new JButton("Đọc file");
		btnReadFile.addActionListener(e -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Chọn file txt");
			fileChooser.setFileFilter(new FileNameExtensionFilter("Tệp văn bản (*.txt)", "txt"));

			int userSelection = fileChooser.showOpenDialog(View.this);

			if (userSelection == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				System.out.println(selectedFile.getAbsolutePath());

				loadFile(selectedFile.getAbsolutePath());
				Control control = new Control(matrixWeight);

				matrixTextArea.setText(control.matrixToString(matrixWeight));
				if (!control.checkUnGraph(matrixWeight)) {
					rdbDirGraph.setSelected(true);

				} else {
					rdbDirGraph.setSelected(false);

				}

			}
		});

		JButton btnPefresh = new JButton("Làm mới");

		btnPefresh.addActionListener(e -> {
			dispose();
			new View();
		});

		additionalFunctionsPanel.add(btnPefresh);
		additionalFunctionsPanel.add(btnReadFile);

		functionPanel.add(subFunctionPanel, BorderLayout.NORTH);

		functionPanel.add(additionalFunctionsPanel, BorderLayout.SOUTH);

		createGraphPanel.add(drawPanel, BorderLayout.CENTER);
		createGraphPanel.add(functionPanel, BorderLayout.EAST);

		resultPanel = new JPanel();
		resultPanel.setPreferredSize(new Dimension(700, 200));
		resultPanel.setLayout(new GridLayout(1, 2));
		resultPanel.setBorder(BorderFactory.createTitledBorder(redBordered, "Hiển thị", TitledBorder.CENTER,
				TitledBorder.TOP, new Font("Tahoma", Font.BOLD, 14), Color.RED));

		resultTextArea = new JTextArea();
		resultTextArea.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Kết quả"));

		matrixTextArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(matrixTextArea);
		scrollPane.setPreferredSize(new Dimension(400, 300)); // thiết lập kích thước cho scrollPane
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		matrixTextArea.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Ma trận"));
		resultPanel.add(resultTextArea);
		resultPanel.add(scrollPane);
		addWindowFocusListener(new WindowFocusListener() {
			@Override
			public void windowGainedFocus(WindowEvent e) {
				// Xử lý sự kiện khi cửa sổ JFrame được focus trở lại
				// Vẽ lại đồ thị ở đây
				try {
					for (DrawGraph point : listVertex) {
						point.drawVertex(getGraphics());
					}
					Graphics g = getGraphics();
					for (int i = 0; i < listVertex.size(); i++) {
						for (DrawGraph point : listVertex) {
							DrawGraph point1 = listVertex.get(i);
							for (int j = i + 1; j < listVertex.size(); j++) {
								DrawGraph point2 = listVertex.get(j);
								int w = matrixWeight[point1.p - 1][point2.p - 1];
								if (w > 0) {
									if (rdbDirGraph.isSelected()) {
										// g.setColor(Color.red);
										g.drawString(Integer.toString(w), (point1.x + point2.x + 10) / 2,
												(point1.y + point2.y) / 2 + 116);
										g.setColor(Color.black);
										point1.drawArrowLine(g, point1.x + 11, point1.y + 100, point2.x + 12,
												point2.y + 100, 5, 5);
									} else {
										// g.setColor(Color.red);
										g.drawString(Integer.toString(w), (point1.x + point2.x + 10) / 2,
												(point1.y + point2.y) / 2 + 116);
										g.setColor(Color.black);
										point1.drawLineWithoutArrow(g, point1.x + 11, point1.y + 100, point2.x + 12,
												point2.y + 100, 0, 0);
									}
								}
							}
						}
					}
				} catch (NullPointerException ex) {
					// System.out.println(ex.getMessage());

				}
			}

			@Override
			public void windowLostFocus(WindowEvent e) {
				// Xử lý sự kiện khi cửa sổ JFrame mất focus
				// Nếu cần thiết, bạn có thể thêm xử lý tại đây
			}

		});

		this.add(topPanel, BorderLayout.NORTH);
		this.add(createGraphPanel, BorderLayout.CENTER);
		this.add(resultPanel, BorderLayout.SOUTH);

	}

	private void loadFile(String filePath) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filePath));

			int size = Integer.parseInt(reader.readLine());
			matrixWeight = new int[size][size];

			String line;
			int row = 0;
			while ((line = reader.readLine()) != null) {
				String[] values = line.split(" ");
				for (int col = 0; col < values.length; col++) {
					matrixWeight[row][col] = Integer.parseInt(values[col]);
				}
				row++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new View();
	}
}
