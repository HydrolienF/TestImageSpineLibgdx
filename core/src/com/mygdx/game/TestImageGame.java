package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.esotericsoftware.spine.SkeletonRenderer;
import com.esotericsoftware.spine.utils.SkeletonActor;

public class TestImageGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Stage stage;

	@Override
	public void create() {
		batch = new SpriteBatch();
		stage = new Stage();

		String imageName = "testImage";
		// String imageName = "ant";
		float scale = 0.1456789f;
		float space = 400;
		int k = 0;

		// texture
		img = new Texture("basic images/" + imageName + ".png");
		img.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

		// actor
		Texture img2 = new Texture("basic images/" + imageName + ".png");
		Actor actor = new Image(img2);
		actor.setPosition(space * (k++), 0);
		actor.setOrigin(Align.center);
		actor.setRotation(10);
		actor.setScale(scale);
		stage.addActor(actor);

		// actor
		Texture img3 = new Texture("basic images/" + imageName + ".png");
		// img3.setFilter(Texture.TextureFilter.MipMapLinearLinear, Texture.TextureFilter.Linear);
		Actor actorLinear = new Image(img3);
		actorLinear.setPosition(space * (k++), 0);
		actorLinear.setOrigin(Align.center);
		actorLinear.setRotation(10);
		actorLinear.setScale(scale);
		stage.addActor(actorLinear);

		// Spine2d
		TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("spine data/" + imageName + "/" + imageName + ".atlas"));

		SkeletonJson json = new SkeletonJson(textureAtlas);
		SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal("spine data/" + imageName + "/skeleton.json"));

		Skeleton skeleton = new Skeleton(skeletonData);

		SkeletonRenderer skeletonRenderer = new SkeletonRenderer();
		skeletonRenderer.setPremultipliedAlpha(true);

		AnimationStateData stateData = new AnimationStateData(skeletonData);

		AnimationState animationState = new AnimationState(stateData);
		// different track index = animation are play at the same time, same track index
		// = animation are play one after the other

		SkeletonActor skeletonActor = new SkeletonActor() {
			@Override
			public void setRotation(float degrees) { getSkeleton().findBone("root").setRotation(degrees); }

			@Override
			public void setScale(float degrees) { getSkeleton().findBone("root").setScale(degrees * 4); }

			@Override
			public float getRotation() { return getSkeleton().findBone("root").getRotation(); }
		};
		skeletonActor.setRenderer(skeletonRenderer);
		skeletonActor.setSkeleton(skeleton);
		skeletonActor.setAnimationState(animationState);

		skeletonActor.setPosition(space * ((k++) + 1.5f), img.getHeight() / 2);
		skeletonActor.setOrigin(Align.center);
		skeletonActor.setRotation(10);
		skeletonActor.setScale(scale);
		stage.addActor(skeletonActor);
	}

	@Override
	public void render() {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		// batch.draw(img, 0, 0);
		for (Actor actor : stage.getActors()) {
			actor.setRotation(actor.getRotation() + Gdx.graphics.getDeltaTime() * 10);
		}
		stage.act();
		stage.draw();
		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
		// img.dispose();
	}
}
