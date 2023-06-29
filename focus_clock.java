import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;
import java.time.*;

// 创建一个番茄时钟类，继承自JFrame
public class PomodoroClock extends JFrame {

    // 定义一些常量
    public static final int WORK_MINUTES = 25; // 工作时间为25分钟
    public static final int BREAK_MINUTES = 5; // 休息时间为5分钟
    public static final int WIDTH = 300; // 窗口宽度
    public static final int HEIGHT = 200; // 窗口高度

    // 定义一些组件
    private JLabel timeLabel; // 显示时间的标签
    private JButton startButton; // 开始按钮
    private JButton stopButton; // 停止按钮
    private JButton resetButton; // 重置按钮
    private Timer timer; // 计时器对象
    private boolean isWorking; // 是否处于工作状态
    private int remainingSeconds; // 剩余秒数

    // 构造方法，初始化窗口和组件
    public PomodoroClock() {
        super("番茄时钟"); // 调用父类构造方法，设置窗口标题
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置窗口关闭时退出程序
        setSize(WIDTH, HEIGHT); // 设置窗口大小
        setLocationRelativeTo(null); // 设置窗口居中显示
        setResizable(false); // 设置窗口不可调整大小

        timeLabel = new JLabel(); // 创建时间标签对象
        timeLabel.setFont(new Font("Arial", Font.BOLD, 36)); // 设置字体大小和样式
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER); // 设置水平居中对齐

        startButton = new JButton("开始"); // 创建开始按钮对象
        startButton.addActionListener(new ActionListener() { // 添加动作监听器
            @Override
            public void actionPerformed(ActionEvent e) {
                start(); // 调用开始方法
            }
        });

        stopButton = new JButton("停止"); // 创建停止按钮对象
        stopButton.addActionListener(new ActionListener() { // 添加动作监听器
            @Override
            public void actionPerformed(ActionEvent e) {
                stop(); // 调用停止方法
            }
        });

        resetButton = new JButton("重置"); // 创建重置按钮对象
        resetButton.addActionListener(new ActionListener() { // 添加动作监听器
            @Override
            public void actionPerformed(ActionEvent e) {
                reset(); // 调用重置方法
            }
        });

        timer = new Timer(1000, new ActionListener() { // 创建计时器对象，每秒触发一次动作监听器
            @Override
            public void actionPerformed(ActionEvent e) {
                tick(); // 调用tick方法，更新时间显示和状态切换
            }
        });

        JPanel buttonPanel = new JPanel(); // 创建一个面板对象，用于放置按钮
        buttonPanel.add(startButton); // 将开始按钮添加到面板中
        buttonPanel.add(stopButton); // 将停止按钮添加到面板中
        buttonPanel.add(resetButton); // 将重置按钮添加到面板中

        add(timeLabel, BorderLayout.CENTER); // 将时间标签添加到窗口的中央位置
        add(buttonPanel, BorderLayout.SOUTH); // 将按钮面板添加到窗口的南边位置

        reset(); // 调用重置方法，初始化时间和状态

        setVisible(true); // 设置窗口可见

    }

    // 开始方法，用于启动计时器和禁用开始按钮和重置按钮
    public void start() {
        timer.start(); // 启动计时器
        startButton.setEnabled(false); // 禁用开始按钮
        resetButton.setEnabled(false); // 禁用重置按钮

    }

    // 停止方法，用于停止计时器和启用开始按钮和重置按钮
    public void stop() {
        timer.stop(); // 停止计时器
        startButton.setEnabled(true); // 启用开始按钮
        resetButton.setEnabled(true); // 启用重置按钮
    }

    // 重置方法，用于重置时间和状态，并更新时间标签
    public void reset() {
        isWorking = true; // 设置状态为工作状态
        remainingSeconds = WORK_MINUTES * 60; // 设置剩余秒数为工作时间
        updateTimeLabel(); // 更新时间标签
    }

    // tick方法，用于每秒更新时间和状态，并检查是否需要切换状态或播放声音
    public void tick() {
        remainingSeconds--; // 剩余秒数减一
        updateTimeLabel(); // 更新时间标签
        if (remainingSeconds == 0) { // 如果剩余秒数为零，说明一个周期结束，需要切换状态或播放声音
            isWorking = !isWorking; // 切换状态
            if (isWorking) { // 如果切换到工作状态
                remainingSeconds = WORK_MINUTES * 60; // 设置剩余秒数为工作时间
                playSound("work.wav"); // 播放工作声音
            } else { // 如果切换到休息状态
                remainingSeconds = BREAK_MINUTES * 60; // 设置剩余秒数为休息时间
                playSound("break.wav"); // 播放休息声音
            }
        }
    }

    // 更新时间标签的方法，用于根据剩余秒数和状态显示相应的文本和颜色
    public void updateTimeLabel() {
        int minutes = remainingSeconds / 60; // 计算剩余分钟数
        int seconds = remainingSeconds % 60; // 计算剩余秒数
        String text = String.format("%02d:%02d", minutes, seconds); // 格式化文本，如25:00
        if (isWorking) { // 如果处于工作状态
            text = "工作 " + text; // 在文本前加上"工作"字样
            timeLabel.setForeground(Color.RED); // 设置文本颜色为红色
        } else { // 如果处于休息状态
            text = "休息 " + text; // 在文本前加上"休息"字样
            timeLabel.setForeground(Color.GREEN); // 设置文本颜色为绿色
        }
        timeLabel.setText(text); // 将文本设置到时间标签上

    }

    // 播放声音的方法，用于根据给定的文件名播放相应的声音文件
    public void playSound(String fileName) {
        try {
            Clip clip = AudioSystem.getClip(); // 创建一个Clip对象，用于播放声音
            clip.open(AudioSystem.getAudioInputStream(getClass().getResource(fileName))); // 打开给定的声音文件，注意文件需要放在与类文件同一目录下
            clip.start(); // 开始播放声音
        } catch (Exception e) {
            e.printStackTrace(); // 如果出现异常，打印异常信息
        }
    }

    // 主方法，用于创建番茄时钟对象并运行程序
    public static void main(String[] args) {
        new PomodoroClock();
    }
}
