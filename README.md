# mouse

自己写的一个网页版的模拟鼠标，还在调整中

开启服务器之后，在任何一个可以执行网页的地方运行socket.html 在手机浏览器打开页面即可控制服务器所在机器的鼠标。
后面计划把tomcat 集成进来，利用tomcat 运行socket.html,在开启鼠标的服务器，手机浏览打开socket.html即可访问

当前版本需要个各种环境配合才可以使用，如要使用，可以按照如下方案

1. 找一个http server,把socket.html 放在服务器上面：

	例如：使用node.js 在电脑上开一个http的服务，把socket.html 放在网页更目录，访问地址即为  http://192.168.1.119:3000/socket.html (根据自己机器和配置修改)

2. 开启 MouseControllWebSocketServer，执行main 方法，运行服务

3. 在手机浏览器打开 **1** 中地址，就可以在页面控制 **2** 所在机器的鼠标了，双击触摸板=左键

这个项目主要是娱乐，之前家里电脑屏幕比较的大，于是躺床上看视频，发现要下床点上一集、下一集好烦，如果手机直接控制电脑就好了。如果尝试写了个小程序。后面有空再修改下，把需要的环境集成进来，方便使用。