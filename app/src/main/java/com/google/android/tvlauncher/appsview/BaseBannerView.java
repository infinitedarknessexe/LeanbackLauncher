package com.google.android.tvlauncher.appsview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Outline;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.tvlauncher.R;
import com.google.android.tvlauncher.util.porting.Edited;
import com.google.android.tvlauncher.util.porting.Reason;

class BaseBannerView extends FrameLayout {
    protected int mAnimDuration;
    protected Drawable mBannerDrawable;
    protected ImageView mBannerImage;
    protected final int mCornerRadius;
    protected float mFocusedScale;
    protected float mFocusedZDelta;
    protected LaunchItem mItem;

    public BaseBannerView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);

        @Edited(reason = Reason.VARIABLE_REUSAGE)
        Resources res = getResources();
        this.mFocusedZDelta = res.getDimension(R.dimen.banner_selected_item_z_delta);
        this.mFocusedScale = res.getFraction(R.fraction.banner_focused_scale, 1, 1); // 2131886081
        this.mAnimDuration = res.getInteger(R.integer.banner_scale_anim_duration);
        this.mCornerRadius = getResources().getDimensionPixelSize(R.dimen.card_rounded_corner_radius);
    }

    public LaunchItem getItem() {
        return this.mItem;
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mBannerImage = ((ImageView) findViewById(R.id.app_banner_image));
        this.mBannerImage.setOutlineProvider(new ViewOutlineProvider() {
            public void getOutline(View paramAnonymousView, Outline paramAnonymousOutline) {
                paramAnonymousOutline.setRoundRect(0, 0, paramAnonymousView.getWidth(), paramAnonymousView.getHeight(), BaseBannerView.this.mCornerRadius);
            }
        });
        setOutlineProvider(new ViewOutlineProvider() {
            public void getOutline(View paramAnonymousView, Outline paramAnonymousOutline) {
                paramAnonymousOutline.setRoundRect(0, 0, paramAnonymousView.getResources().getDimensionPixelSize(R.dimen.banner_width), paramAnonymousView.getResources().getDimensionPixelSize(2131558500), BaseBannerView.this.mCornerRadius);
            }
        });
        this.mBannerImage.setClipToOutline(true);
    }

    public void setLaunchItem(LaunchItem paramLaunchItem) {
        this.mItem = paramLaunchItem;
        this.mBannerDrawable = this.mItem.getItemDrawable().getConstantState().newDrawable();
        this.mBannerImage.setImageDrawable(this.mBannerDrawable);
        if (((this.mBannerDrawable instanceof BitmapDrawable)) && (((BitmapDrawable) this.mBannerDrawable).getBitmap().hasAlpha())) {
            this.mBannerImage.setBackgroundColor(ContextCompat.getColor(getContext(), 2131820551));
            return;
        }
        this.mBannerImage.setBackground(null);
    }
}


/* Location:              ~/Downloads/fugu-opr2.170623.027-factory-d4be396e/fugu-opr2.170623.027/image-fugu-opr2.170623.027/TVLauncher/TVLauncher/TVLauncher-dex2jar.jar!/com/google/android/tvlauncher/appsview/BaseBannerView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */