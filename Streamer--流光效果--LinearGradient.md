# Streamer--流光效果--LinearGradient

**Reference URL** 
https://www.jianshu.com/p/32d17739d378
https://www.jianshu.com/p/98d576e1bff6

线性渐变---只能是45度角整数倍的渐变[0,45,90,135,180]

## XML中实现渐变[只能单重渐变]

```
在drawable文件家中创建xml文件   ShapeDrawable
type                   类型 linear radial sweep 三种类型
angle                  起始角度，0 左边；90 下边，180，右边，270 上边。共8个方向，从0开始每次增加45
centerX centerY            中心点所在的百分比
endColor startColor        开始颜色，结束颜色
centerColor                中心点颜色
gradientRadius         渐变半径 android:type="radial" 才有作用

例如：shape_gradient.xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <gradient
        android:angle="90"
        android:endColor="@color/colorPrimary"
        android:startColor="@color/colorAccent" />
</shape>

android:background="@drawable/shape_gradient"
```

## Java中实现渐效果[多重渐变(color1->color2->...->colorN)]

```
LinearGradient.java  线性渐变  渲染只能是矩形或正方形，不能是平行四边形，不然出现渲染效果不准确问题
线性渐变就是在线性方向的的渐变。有两个构造函数
/**
    @param x0           起始点X坐标
    @param y0           起始点Y坐标
    @param x1           终点X坐标
    @param y1           终点Y坐标
    @param  colors      所有颜色渐变集合
    @param  positions   我们可以让它均匀的渐变，也可以让它按照你想要的比例进行渐变，可以为null，
                        这样的话假设1为整个渐变的长度，我们设置的所有颜色（假设有4种颜色），都以
                        同等的权重（渐变长度比例0.25：0.25：0.25：0.25）进行颜色渐变。
    @param  tile        着色器的不同模式
 */
public LinearGradient(float x0, float y0, float x1, float y1, int colors[], float positions[],TileMode tile)
LinearGradient(float x0, float y0, float x1, float y1, int color0, int color1, Shader.TileMode tile)

Paint.setShader(LinearGradient);

0  度角渐变---- (0,0) 到 (width,0)
45 度角渐变---- (0,0) 到 (width,height)

如何实现其他角度渐变----  先按45度角的整倍数渐变，在通过canvas.rotate(角度,宽，高)进行角度旋转

RadialGradient.java  是圆环一样的的渐变，RadialGradient 同样是两个构造函数
/**
   @param float centerX, float centerY 渐变的中心点 圆心
   @param float radius 渐变的半径
   @param int[] colors 渐变颜色数组
   @param float[] stops 和颜色数组对应， 每种颜色在渐变方向上所占的百分比取值[0, 1]
   @param Shader.TileMode tileMode 表示绘制完成，还有剩余空间的话的绘制模式。
   int centerColor, int edgeColor 中心点颜色和边缘颜色
*/
RadialGradient(float centerX, float centerY, float radius, int[] colors, float[] stops, Shader.TileMode tileMode)
RadialGradient(float centerX, float centerY, float radius, int centerColor, int edgeColor, Shader.TileMode tileMode)

SweepGradient.java   和角度有关的渐变。以某一点为圆心，随着角度的大小发生渐变。

/**
   @param float cx, float cy 中心点坐标
   @param int[] colors 颜色数组
   @param float[] positions 数组颜色在渐变方向上所占的百分比
   1.float cx, float cy 中心点坐标
   2.int color0, int color1 开始颜色 结束颜色
*/
SweepGradient(float cx, float cy, int[] colors, float[] positions)
SweepGradient(float cx, float cy, int color0, int color1)


Shader.TileMode 类型，决定了如果View还有剩余空间，如何绘制。
```
**Dialog设置外部不可点击**
方法一： setCanceledOnTouchOutside(false);调用这个方法时，按对话框以外的地方不起作用。按返回键还起作用 方法二： setCanceleable(false);调用这个方法时，按对话框以外的地方不起作用。按返回键也不起作用 

## 仿斗鱼滑动拼图验证码控件

https://blog.csdn.net/c10WTiybQ1Ye3/article/details/78098657

https://blog.csdn.net/u013904672/article/details/51279520

https://blog.csdn.net/a214024475/article/details/53320004

https://www.jianshu.com/p/2fbeabf192f6

**安卓渐变效果矩形可以规则渐变，在平行四边形上出现渐变混乱？如何实现平行四边规则渐变？**
貌似渲染渐变效果在矩形和圆上才比较规则，平行四边形比较混乱，Android的Bug吧
1：使用矩形规则渐变，通过转动矩形角度，形成平行四边形规则效果。
2：叫UI P图



android  流光动画  <https://www.jianshu.com/p/a49b177655d6> 

android 绘图Shader   <https://www.jianshu.com/p/1efcc9c9f286> 

android 字体流光特效  <https://blog.csdn.net/hyf97135/article/details/53559497> 

android decorView 浅析 <http://www.cnblogs.com/ldq2016/p/6671501.html> 

WebViewClient 用法详解   <https://blog.csdn.net/harvic880925/article/details/51523983> 

Android ImageView放置到布局中心

ImageView显示LinearLayout中，即水平居中和垂直居中，但是layout_gravity属性不好使

使用RelativeLayout包裹ImageView，设置ImageView的属性android:layout_centerInparent="true"

## RelativeLayout是相对布局，用到的一些重要的属性：

```
第一类:属性值为true或false
android:layout_centerHrizontal     水平居中
android:layout_centerVertical        垂直居中
android:layout_centerInparent      相对于父元素完全居中
android:layout_alignParentBottom  贴紧父元素的下边缘
android:layout_alignParentLeft      贴紧父元素的左边缘
android:layout_alignParentRight   贴紧父元素的右边缘
android:layout_alignParentTop      贴紧父元素的上边缘
android:layout_alignWithParentIfMissing
如果对应的兄弟元素找不到的话就以父元素做参照物

第二类：属性值必须为id的引用名“@id/id-name”
android:layout_below            在某元素的下方
android:layout_above             在某元素的的上方
android:layout_toLeftOf         在某元素的左边
android:layout_toRightOf       在某元素的右边

android:layout_alignTop         本元素的上边缘和某元素的的上边缘对齐
android:layout_alignLeft         本元素的左边缘和某元素的的左边缘对齐
android:layout_alignBottom   本元素的下边缘和某元素的的下边缘对齐
android:layout_alignRight      本元素的右边缘和某元素的的右边缘对齐
```

