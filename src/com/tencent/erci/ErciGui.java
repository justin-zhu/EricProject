package com.tencent.erci;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;

import com.tencent.utils.PathBuild;
import com.tencent.utils.ThreadPool;
import com.tencent.utils.WindowAutoHide;

@SuppressWarnings("serial")
public class ErciGui extends JFrame {

	private JPanel contentPane;
	private String currentID;
	JComboBox<String> comboBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		ThreadPool.getCachedThreadPool().execute(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.pagosoft.plaf.PgsLookAndFeel");
					ErciGui frame = new ErciGui();
					new WindowAutoHide(frame);
					frame.setTitle("二次元设备集成工具 by Justin");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ErciGui() {
		currentID = "请选择";
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 680, 380);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel() {
			// @Override
			// protected void paintComponent(Graphics g) {
			// String path = "back.jpg";
			// Image im = null;
			// try {
			// im = ImageIO.read(this.getClass().getResource(path));
			// } catch (IOException e) {
			// e.printStackTrace();
			// }
			// g.drawImage(im, 0, 0, 680, 380, this);
			// }
		};
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel lblsn = new JLabel("选择设备ID:");
		lblsn.setBounds(10, 25, 73, 15);
		panel.add(lblsn);

		comboBox = new JComboBox<String>();
		addItem(comboBox);
		comboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {

				if (e.getStateChange() == ItemEvent.SELECTED) {
					currentID = e.getItem().toString();
					System.out.println(DateUtil.date(System.currentTimeMillis()) + ":当前设备：" + currentID);
				}
			}
		});
		comboBox.setBounds(96, 22, 152, 21);
		panel.add(comboBox);

		JButton btnlog = new JButton("抓取LOG");
		btnlog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isCheckDevice()) {
					String cmd = "adb -s " + currentID + " logcat *:V>" + getPartition();
					startCMD(cmd);
				}
			}
		});
		btnlog.setBounds(10, 68, 114, 23);
		panel.add(btnlog);

		JButton button = new JButton("清理环境");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isCheckDevice()) {
					StringBuffer buffer = new StringBuffer();
					buffer.append("call adb -s " + currentID + " root" + "\r\n").append("call adb -s " + currentID + " remount" + "\r\n").append("call adb -s " + currentID + " shell rm /sdcard/Download/com.tencent.saber/com.tencent.saber.apk" + "\r\n")
							.append("call adb -s " + currentID + " shell rm /data/ota_package/saber_update.zip" + "\r\n").append("call adb -s " + currentID + " shell rm /system/app/sabersdk/sabersdk.apk" + "\r\n")
							.append("call adb -s " + currentID + " shell rm /system/app/saberlillian/saberlillian.apk" + "\r\n").append("call adb -s " + currentID + " reboot");
					startCMD(buffer.toString());
				}
			}
		});
		button.setBounds(134, 68, 114, 23);
		panel.add(button);

		JButton btnrom = new JButton("查看ROM版本");
		btnrom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isCheckDevice()) {
					StringBuffer buffer = new StringBuffer();
					buffer.append("adb -s " + currentID + " shell getprop | findstr version" + "\r\n").append("pause");
					startCMD(buffer.toString());
				}
			}
		});
		btnrom.setBounds(10, 123, 114, 23);
		panel.add(btnrom);

		JButton btnsdk = new JButton("查看SDK版本");
		btnsdk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isCheckDevice()) {
					StringBuffer buffer = new StringBuffer();
					buffer.append("adb -s " + currentID + " shell dumpsys package com.tencent.saber | findstr version" + "\r\n").append("pause");
					startCMD(buffer.toString());
				}
			}
		});
		btnsdk.setBounds(509, 68, 110, 23);
		panel.add(btnsdk);

		JButton button_2 = new JButton("设置静默时间");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isCheckDevice()) {
					StringBuffer buffer = new StringBuffer();
					buffer.append("adb -s " + currentID + "  root" + "\r\n").append("adb -s " + currentID + " remount" + "\r\n").append("adb -s " + currentID + " shell date -D MMDDhhmmCCYY.ss 111901382018.01");
					startCMD(buffer.toString());
				}
			}
		});
		button_2.setBounds(385, 68, 114, 23);
		panel.add(button_2);

		JButton btnNewButton = new JButton("查看设备时间");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isCheckDevice()) {
					StringBuffer buffer = new StringBuffer();
					buffer.append("adb -s " + currentID + " shell date" + "\r\n").append("pause");
					startCMD(buffer.toString());
				}
			}
		});
		btnNewButton.setBounds(134, 123, 114, 23);
		panel.add(btnNewButton);

		JButton button_3 = new JButton("查看硬盘信息");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isCheckDevice()) {
					StringBuffer buffer = new StringBuffer();
					buffer.append("adb -s " + currentID + " shell df -h" + "\r\n").append("pause");
					startCMD(buffer.toString());
				}
			}
		});
		button_3.setBounds(261, 123, 114, 23);
		panel.add(button_3);

		JButton button_1 = new JButton("重启设备");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isCheckDevice()) {
					String cmd = "adb -s " + currentID + " reboot";
					startCMD(cmd);
				}
			}
		});
		button_1.setBounds(258, 68, 117, 23);
		panel.add(button_1);

		JButton button_4 = new JButton("刷新设备");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addItem(comboBox);
			}
		});
		button_4.setBounds(258, 21, 93, 23);
		panel.add(button_4);

		JButton button_5 = new JButton("检查更新状态");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isCheckDevice()) {
					String cmd = "adb -s " + currentID + " logcat *:V | find \"ota.OtaManager\"";
					startCMD(cmd);
				}
			}
		});
		button_5.setBounds(385, 123, 114, 23);
		panel.add(button_5);
		setVisible(true);
	}

	public void addItem(JComboBox<String> box) {
		box.removeAllItems();
		HashSet<String> set = GetDevices.getDevices();
		box.addItem("请选择");
		for (String string : set) {
			box.addItem(string.trim());
		}
	}

	public void startCMD(final String cmd) {
		ThreadPool.getCachedThreadPool().execute(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println(cmd);
					Runtime.getRuntime().exec("cmd.exe /k start " + PathBuild.getPath(cmd));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	public String getPartition() {
		String now = DateUtil.now().replaceAll("[\\pP\\pS\\pZ]", "_");
		System.out.println(now);
		File[] roots = File.listRoots();
		int index = roots.length;
		String partition = roots[(index - 1)].toString();
		File file = new File(partition + "\\log", currentID + "_" + now + ".txt");
		FileUtil.touch(file);
		return file.getAbsolutePath();
	}

	public boolean isCheckDevice() {
		if ("请选择".equals(currentID)) {
			JOptionPane.showMessageDialog(null, "请选择设备后，再执行操作");
			return false;
		}
		return true;
	}

	class Background extends JPanel {
		/**
		 * 画一个背景图
		 */
		private static final long serialVersionUID = 1L;
		Image im;

		public Background() {
			String path = "back.jpg";
			try {
				im = ImageIO.read(this.getClass().getResource(path));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void paintComponent(Graphics g) {
			g.drawImage(im, 0, 0, 400, 300, this);
		}
	}
}
