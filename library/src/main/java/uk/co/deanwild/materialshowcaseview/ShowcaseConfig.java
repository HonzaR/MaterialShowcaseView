package uk.co.deanwild.materialshowcaseview;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

import uk.co.deanwild.materialshowcaseview.shape.CircleShape;
import uk.co.deanwild.materialshowcaseview.shape.Shape;


public class ShowcaseConfig {

    public static final String DEFAULT_MASK_COLOUR = "#dd335075";
    public static final long DEFAULT_FADE_TIME = 300;
    public static final long DEFAULT_DELAY = 0;
    public static final Shape DEFAULT_SHAPE = new CircleShape();
    public static final int DEFAULT_SHAPE_PADDING = 10;

    private long mDelay = DEFAULT_DELAY;
    private int mMaskColour;
    private Typeface mTextsStyle = Typeface.DEFAULT;

    private int mTitleTextColor;
    private int mContentTextColor;
    private int mDismissTextColor;
    private long mFadeDuration = DEFAULT_FADE_TIME;
    private Shape mShape = DEFAULT_SHAPE;
    private int mShapePadding = DEFAULT_SHAPE_PADDING;
    private boolean renderOverNav = false;
    private boolean dismissOnTouch = false;
    private boolean showDismissButton = true;
    private Drawable mDismissBackground;
    private float mTitleTextSize;
    private float mContentTextSize;
    private boolean mCloseEnabled;
    private boolean mNextEnabled;

    public ShowcaseConfig() {
        mMaskColour = Color.parseColor(ShowcaseConfig.DEFAULT_MASK_COLOUR);
        mTitleTextColor = Color.parseColor("#ffffff");
        mContentTextColor = Color.parseColor("#ffffff");
        mDismissTextColor = Color.parseColor("#ffffff");
    }

    public long getDelay() {
        return mDelay;
    }

    public void setDelay(long delay) {
        this.mDelay = delay;
    }

    public int getMaskColor() {
        return mMaskColour;
    }

    public void setMaskColor(int maskColor) {
        mMaskColour = maskColor;
    }

    public int getContentTextColor() {
        return mContentTextColor;
    }

    public void setContentTextColor(int mContentTextColor) {
        this.mContentTextColor = mContentTextColor;
    }

    public int getTitleTextColor() {
        return mTitleTextColor;
    }

    public void setTitleTextColor(int mTitleTextColor) {
        this.mTitleTextColor = mTitleTextColor;
    }

    public float getTitleTextSize() {
        return mTitleTextSize;
    }

    public void setTitleTextSize(float size) {
        this.mTitleTextSize = size;
    }

    public int getDismissTextColor() {
        return mDismissTextColor;
    }

    public void setDismissTextColor(int dismissTextColor) {
        this.mDismissTextColor = dismissTextColor;
    }

    public Typeface getTextsStyle() {
        return mTextsStyle;
    }

    public void setTextsStyle(Typeface textsStyle) {
        this.mTextsStyle = textsStyle;
    }

    public void setDismissBackground(Drawable background) {
        this.mDismissBackground = background;
    }
    public Drawable getDismissBackground() {
        return this.mDismissBackground;
    }

    public void setContentTextSize(float size) {
        this.mContentTextSize = size;
    }

    public float getContentTextSize() {
        return mContentTextSize;
    }

    public long getFadeDuration() {
        return mFadeDuration;
    }

    public void setFadeDuration(long fadeDuration) {
        this.mFadeDuration = fadeDuration;
    }

    public Shape getShape() {
        return mShape;
    }

    public void setShape(Shape shape) {
        this.mShape = shape;
    }

    public void setShapePadding(int padding) {
        this.mShapePadding = padding;
    }

    public int getShapePadding() {
        return mShapePadding;
    }

    public boolean getRenderOverNavigationBar() {
        return renderOverNav;
    }

    public void setRenderOverNavigationBar(boolean renderOverNav) {
        this.renderOverNav = renderOverNav;
    }

    public boolean getDismissOnTouch() {
        return dismissOnTouch;
    }

    public void setDismissOnTouch(boolean dismissOnTouch) {
        this.dismissOnTouch = dismissOnTouch;
    }

    public void setShowDismissButton(boolean enable) {
        this.showDismissButton = enable;
    }

    public boolean getShowDismissButton() {
        return showDismissButton;
    }

    public boolean getCloseEnabled() {
        return mCloseEnabled;
    }

    public void setCloseEnabled(boolean closeEnabled) {
        this.mCloseEnabled = closeEnabled;
    }

    public boolean getNextEnabled() {
        return mNextEnabled;
    }

    public void setNextEnabled(boolean nextEnabled) {
        this.mNextEnabled = nextEnabled;
    }
}
