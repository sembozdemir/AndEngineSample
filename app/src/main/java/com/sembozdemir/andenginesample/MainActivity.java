package com.sembozdemir.andenginesample;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.color.Color;

import java.io.IOException;


public class MainActivity extends SimpleBaseGameActivity {

    private Camera camera;
    private static final int CAMERA_WIDTH = 480;
    private static final int CAMERA_HEIGHT = 800;

    private BitmapTextureAtlas myTexture;
    private ITextureRegion myTextureRegion;

    private Sprite mySprite;

    @Override
    public EngineOptions onCreateEngineOptions() {
        camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED,
                new FillResolutionPolicy(), camera);

        return engineOptions;
    }

    @Override
    protected void onCreateResources() throws IOException {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        myTexture = new BitmapTextureAtlas(getTextureManager(), 256, 256, TextureOptions.DEFAULT);
        myTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myTexture, this, "image.png", 0, 0);
        myTexture.load();
    }

    @Override
    protected Scene onCreateScene() {

        Scene scene = new Scene();
        scene.setBackground(new Background(Color.CYAN));

        mySprite = new Sprite(CAMERA_WIDTH/2, CAMERA_HEIGHT/2, myTextureRegion, mEngine.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.isActionUp()) {
                    float leftlimit = mySprite.getWidth()/2;
                    float rightlimit = CAMERA_WIDTH - mySprite.getWidth()/2;
                    float lowlimit = mySprite.getHeight()/2;
                    float highlimit = CAMERA_HEIGHT - mySprite.getHeight()/2;

                    float newX = (int)(Math.random() * ((rightlimit - leftlimit) + 1) + leftlimit);
                    float newY = (int)(Math.random() * ((highlimit - lowlimit) + 1) + lowlimit);
                    mySprite.setPosition(newX, newY);
                }

                return true;
            }
        };

        scene.registerTouchArea(mySprite);
        scene.attachChild(mySprite);

        return scene;
    }


}
