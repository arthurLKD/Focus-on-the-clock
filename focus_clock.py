# 导入time模块
import time

# 定义一个专注时钟类
class FocusClock:

    # 初始化方法，设置专注时间和休息时间（单位为秒）
    def __init__(self, focus_time, break_time):
        self.focus_time = focus_time
        self.break_time = break_time

    # 开始专注时钟的方法
    def start(self):
        # 循环执行以下操作
        while True:
            # 调用focus方法，进行专注
            self.focus()
            # 调用break方法，进行休息
            self.break()

    # 专注的方法
    def focus(self):
        # 打印开始专注的提示信息
        print("开始专注！")
        # 计时focus_time秒
        time.sleep(self.focus_time)
        # 打印结束专注的提示信息
        print("结束专注！")

    # 休息的方法
    def break(self):
        # 打印开始休息的提示信息
        print("开始休息！")
        # 计时break_time秒
        time.sleep(self.break_time)
        # 打印结束休息的提示信息
        print("结束休息！")

# 创建一个专注时钟对象，设置专注时间为25分钟，休息时间为5分钟
focus_clock = FocusClock(25*60, 5*60)

# 调用start方法，开始专注时钟
focus_clock.start()
