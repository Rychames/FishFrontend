package com.example.fishcontroller.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.TransitionDrawable;
import android.widget.ToggleButton;

public class AnimationUtils {

    public static void animateToggleButton(ToggleButton toggleButton) {
        // Verifique se o background é realmente um TransitionDrawable
        if (!(toggleButton.getBackground() instanceof TransitionDrawable)) {
            return; // Sai se o background não for um TransitionDrawable
        }

        // Animação de escala
        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(toggleButton, "scaleX", 0.9f);
        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(toggleButton, "scaleY", 0.9f);
        scaleDownX.setDuration(100);
        scaleDownY.setDuration(100);

        ObjectAnimator scaleUpX = ObjectAnimator.ofFloat(toggleButton, "scaleX", 1f);
        ObjectAnimator scaleUpY = ObjectAnimator.ofFloat(toggleButton, "scaleY", 1f);
        scaleUpX.setDuration(100);
        scaleUpY.setDuration(100);

        AnimatorSet scaleDown = new AnimatorSet();
        scaleDown.play(scaleDownX).with(scaleDownY);

        AnimatorSet scaleUp = new AnimatorSet();
        scaleUp.play(scaleUpX).with(scaleUpY);

        AnimatorSet scaleAnim = new AnimatorSet();
        scaleAnim.playSequentially(scaleDown, scaleUp);
        scaleAnim.start();

        // Efeito de transição
        TransitionDrawable transition = (TransitionDrawable) toggleButton.getBackground();
        if (toggleButton.isChecked()) {
            transition.startTransition(300); // Aplica transição para o estado "on"
        } else {
            transition.reverseTransition(300); // Reverte transição para o estado "off"
        }
    }
}