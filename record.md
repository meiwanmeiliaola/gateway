### 审核流程

```flow
st=>start: 开始
op1=>operation: 提交内容
op2=>operation: 修改
op3=>operation: 发布
c1=>condition: 检查审核
e=>end

st->op1->c1
c1(yes)->op3
c1(no)->op2->op1

```

### 消息系统
系统通知 
    审核通过消息 
    新发布消息
私信
 
 id
 消息内容
 消息类型  系统/信息/
 发送时间
 是否已读
 发送人
 接收人
  