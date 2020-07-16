package uk.co.deanwild.materialshowcaseview;

import android.app.Activity;
import android.view.View;

import java.util.LinkedList;
import java.util.Queue;


public class MaterialShowcaseSequence implements IDetachedListener {

    PrefsManager mPrefsManager;
    Queue<MaterialShowcaseView> mShowcaseQueue;
    private MaterialShowcaseView mCurrentSequenceItem;
    private boolean mSingleUse = false;
    Activity mActivity;
    private ShowcaseConfig mConfig;
    private int mSequencePosition = 0;
    private String mSequenceID;
    private boolean mIsShowing = false;

    private OnSequenceItemShownListener mOnItemShownListener = null;
    private OnSequenceItemDismissedListener mOnItemDismissedListener = null;
    private OnSequenceItemClosedListener mOnItemClosedListener = null;
    private OnSequenceItemNextListener mOnItemNextListener = null;

    public MaterialShowcaseSequence(Activity activity) {
        mActivity = activity;
        mShowcaseQueue = new LinkedList<>();
    }

    public MaterialShowcaseSequence(Activity activity, String sequenceID) {
        this(activity);
        this.singleUse(sequenceID);
    }

    public MaterialShowcaseSequence addSequenceItem(View targetView, String content, String dismissText) {
        addSequenceItem(targetView, "", content, dismissText, "", "");
        return this;
    }

    public MaterialShowcaseSequence addSequenceItem(View targetView, String title, String content, String dismissText) {
        addSequenceItem(targetView, title, content, dismissText, "", "");
        return this;
    }

    public MaterialShowcaseSequence addSequenceItem(View targetView, String title, String content, String dismissText, String closeText, String nextText) {

        MaterialShowcaseView sequenceItem = new MaterialShowcaseView.Builder(mActivity)
                .setTarget(targetView)
                .setTitleText(title)
                .setDismissText(dismissText)
                .setContentText(content)
                .setCloseText(closeText)
                .setNextText(nextText)
                .build();

        if (mConfig != null) {
            sequenceItem.setConfig(mConfig);
        }

        mShowcaseQueue.add(sequenceItem);
        return this;
    }

    public MaterialShowcaseSequence addSequenceItem(MaterialShowcaseView sequenceItem) {
        mShowcaseQueue.add(sequenceItem);
        return this;
    }

    public MaterialShowcaseSequence singleUse(String sequenceID) {
        mSingleUse = true;
        mSequenceID = sequenceID;
        mPrefsManager = new PrefsManager(mActivity, sequenceID);
        return this;
    }

    public void setOnItemShownListener(OnSequenceItemShownListener listener) {
        this.mOnItemShownListener = listener;
    }

    public void setOnItemDismissedListener(OnSequenceItemDismissedListener listener) {
        this.mOnItemDismissedListener = listener;
    }

    public void setOnItemClosedListener(OnSequenceItemClosedListener listener) {
        this.mOnItemClosedListener = listener;
    }

    public void setOnItemNextListener(OnSequenceItemNextListener listener) {
        this.mOnItemNextListener = listener;
    }

    public boolean hasFired() {

        if (mPrefsManager.getSequenceStatus() == PrefsManager.SEQUENCE_FINISHED) {
            return true;
        }

        return false;
    }

    public boolean isShowing() {
        return mIsShowing;
    }

    public void start() {

        /**
         * Check if we've already shot our bolt and bail out if so         *
         */
        if (mSingleUse) {
            if (hasFired()) {
                return;
            }

            /**
             * See if we have started this sequence before, if so then skip to the point we reached before
             * instead of showing the user everything from the start
             */
            mSequencePosition = mPrefsManager.getSequenceStatus();

            if (mSequencePosition > 0) {
                for (int i = 0; i < mSequencePosition; i++) {
                    mShowcaseQueue.poll();
                }
            }
        }


        // do start
        if (mShowcaseQueue.size() > 0) {
            mIsShowing = true;
            showNextItem();
        }
    }

    public void stop()
    {
        if (mShowcaseQueue != null && !mActivity.isFinishing() && isShowing()) {

            if (mCurrentSequenceItem != null)
                mCurrentSequenceItem.hide();

            mShowcaseQueue.clear();

            if (mSingleUse) {
                mPrefsManager.setFired();
            }

            mIsShowing = false;
        }
    }

    public String getSequenceID() {
        return mSequenceID;
    }

    private void showNextItem() {

        if (mShowcaseQueue.size() > 0 && !mActivity.isFinishing()) {
            mCurrentSequenceItem = mShowcaseQueue.remove();
            mCurrentSequenceItem.setDetachedListener(this);
            mCurrentSequenceItem.show(mActivity);
            if (mOnItemShownListener != null) {
                mOnItemShownListener.onShow(mCurrentSequenceItem, mSequencePosition);
            }
            if (mOnItemClosedListener != null) {
                mCurrentSequenceItem.setCloseListener(new ICloseListener() {
                    @Override
                    public void onShowcaseClosed(MaterialShowcaseView showcaseView) {
                        mOnItemClosedListener.onClosed(mCurrentSequenceItem, mSequencePosition);
                        stop();
                    }
                });
            }
            if (mOnItemNextListener != null) {
                mCurrentSequenceItem.setNextListener(new INextListener() {
                    @Override
                    public void onShowcaseClosed(MaterialShowcaseView showcaseView) {
                        mOnItemNextListener.onNext(mCurrentSequenceItem, mSequencePosition);
//                        showcaseView.hide();
//                        showNextItem();
                    }
                });
            }
        } else {
            /**
             * We've reached the end of the sequence, save the fired state
             */
            if (mSingleUse) {
                mPrefsManager.setFired();
            }

            mIsShowing = false;
        }
    }


    @Override
    public void onShowcaseDetached(MaterialShowcaseView showcaseView, boolean wasDismissed) {

        showcaseView.setDetachedListener(null);

        /**
         * We're only interested if the showcase was purposefully dismissed
         */
        if (wasDismissed) {

            if (mOnItemDismissedListener != null) {
                mOnItemDismissedListener.onDismiss(showcaseView, mSequencePosition);
            }

            /**
             * If so, update the prefsManager so we can potentially resume this sequence in the future
             */
            if (mPrefsManager != null) {
                mSequencePosition++;
                mPrefsManager.setSequenceStatus(mSequencePosition);
            }

            showNextItem();
        }
    }

    public void setConfig(ShowcaseConfig config) {
        this.mConfig = config;

        if (mShowcaseQueue != null) {
            for (MaterialShowcaseView view : mShowcaseQueue)
                view.setConfig(config);
        }
    }

    public interface OnSequenceItemShownListener {
        void onShow(MaterialShowcaseView itemView, int position);
    }

    public interface OnSequenceItemDismissedListener {
        void onDismiss(MaterialShowcaseView itemView, int position);
    }

    public interface OnSequenceItemClosedListener {
        void onClosed(MaterialShowcaseView itemView, int position);
    }

    public interface OnSequenceItemNextListener {
        void onNext(MaterialShowcaseView itemView, int position);
    }

}
