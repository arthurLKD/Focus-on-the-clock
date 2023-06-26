import time 

FOCUS_TIME = 25 * 60  
BREAK_TIME = 5 * 60  

def print_message(message):
    print(f'{message}!')

def focus(): 
    print_message('开始专注')
    time.sleep(FOCUS_TIME)
    print_message('结束专注')

def break():
    print_message('开始休息')
    time.sleep(BREAK_TIME) 
    print_message('结束休息')
    
total_time = 3 * 60 * 60  
start_time = time.time()
while time.time() - start_time < total_time:
    focus()
    break() 
print('时间到,退出专注时钟!')  
```