package edu.augustana.csc285.gamebuilder;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.imageio.ImageIO;

import edu.augustana.csc285.game.datamodel.*;

public class SlideEditor {

	private int currentSlideIndex;
	private GameData data;

	public SlideEditor(GameData data) {
		this.currentSlideIndex = -1;
		this.data = data;
	}

	public int getCurrentSlide() {
		return currentSlideIndex;
	}

	public void setCurrentSlide(int currentRoom) {
		this.currentSlideIndex = currentRoom;
	}

	public void changeTitle(String title) {
		data.getSlide(currentSlideIndex).setTitle(title);

	}

	public void setGameText(String text) {
		data.getSlide(currentSlideIndex).setGameText(text);
	}

	public void addActionChoice() {
		data.getSlide(currentSlideIndex).addActionChoice();

	}

	// https://stackoverflow.com/questions/16433915/how-to-copy-file-from-one-location-to-another-location
	// as a reference
	public void setSlideImage(File slideImageIn) throws IOException {

		String path = "assets/slideImages/SlideImageNumber" + currentSlideIndex + ".jpg";
		Files.copy(slideImageIn.toPath(), (new File(path)).toPath(), StandardCopyOption.REPLACE_EXISTING);
		data.getSlide(currentSlideIndex).setImageFileName(path);
	}
}
