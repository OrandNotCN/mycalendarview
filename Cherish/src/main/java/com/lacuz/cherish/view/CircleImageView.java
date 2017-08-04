package com.lacuz.cherish.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 圆形ImageView，可设置最多两个宽度不同且颜色不同的圆形边框。 设置颜色在xml布局文件中由自定义属性配置参数指定
 */
public class CircleImageView extends android.support.v7.widget.AppCompatImageView {
	private int mBorderWidth = 5;
	private int mBorderColor = Color.parseColor("#f2f2f2");

	public CircleImageView(Context context) {
		super(context);
	}

	public CircleImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Drawable drawable = getDrawable();
		if (drawable == null) {
			return;
		}
		if (getWidth() == 0 || getHeight() == 0) {
			return;
		}
		this.measure(0, 0);
		if (drawable.getClass() == NinePatchDrawable.class)
			return;
		if (((BitmapDrawable) drawable).getBitmap().isRecycled()
				|| ((BitmapDrawable) drawable).getBitmap() == null)
			return;
		Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap().copy(
				Bitmap.Config.ARGB_8888, true);
		final int width = getWidth();
		final int height = getHeight();

		Bitmap roundBitmap = getCroppedRoundBitmap(bitmap, width, height);
		canvas.drawBitmap(roundBitmap, 0, 0, null);
		// setImageBitmap(roundBitmap);
		// super.onDraw(canvas);
		drawBorder(canvas, width, height);
	}

	Bitmap currentBitmap;

	public void recycle() {
		if ((this.currentBitmap == null) || (this.currentBitmap.isRecycled()))
			return;
		this.currentBitmap.recycle();
		this.currentBitmap = null;
		setImageBitmap(null);
	}

	@Override
	public void setImageBitmap(Bitmap bm) {
		currentBitmap = bm;
		super.setImageBitmap(currentBitmap);
	}

	/**
	 * 获取裁剪后的圆形图片
	 *
	 */
	public Bitmap getCroppedRoundBitmap(Bitmap bmp, final int width,
			final int height) {

		Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		Paint paint = new Paint();

		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);
		// canvas.drawARGB(0, 0, 0, 0);
		// canvas.drawCircle(width >> 1, height >> 1, (width - mBorderWidth
		// *2)>>1 ,paint);
		final int padding = mBorderWidth != 0 ? mBorderWidth - 2 : 0;
		RectF localRectF = new RectF(padding, padding, width - padding, height
				- padding);
		canvas.drawOval(localRectF, paint);

		// 产生一个红色的圆角矩形
		// canvas.drawRoundRect(localRectF, 30, 30, paint);

		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bmp, null, localRectF, paint);
		bmp = null;

		return output;
	}

	/**
	 * 边缘画圆
	 */
	private void drawBorder(Canvas canvas, final int width, final int height) {
		if (mBorderWidth == 0) {
			return;
		}
		final Paint mBorderPaint = new Paint();
		mBorderPaint.setStyle(Paint.Style.STROKE);
		mBorderPaint.setAntiAlias(true);
		mBorderPaint.setColor(mBorderColor);
		mBorderPaint.setStrokeWidth(mBorderWidth);
		/**
		 * 坐标x：view宽度的一般 坐标y：view高度的一般 半径r：因为是view宽度的一半-border
		 */
		 canvas.drawCircle(width >> 1, height >> 1, (width >> 1) -
		 mBorderWidth, mBorderPaint);
//		RectF localRectF = new RectF(mBorderWidth, mBorderWidth, width
//				- mBorderWidth, height - mBorderWidth);
//		canvas.drawRoundRect(localRectF, 30, 30, mBorderPaint);
		canvas = null;
	}
}