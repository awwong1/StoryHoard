package ca.ualberta.cmput301f13t13.storyhoard.local;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;
import ca.ualberta.cmput301f13t13.storyhoard.dataClasses.Chapter;
import ca.ualberta.cmput301f13t13.storyhoard.dataClasses.Choice;
import ca.ualberta.cmput301f13t13.storyhoard.dataClasses.Media;
import ca.ualberta.cmput301f13t13.storyhoard.dataClasses.Story;

public class Syncher {
	private static StoryManager storyMan = null;
	private static ChapterManager chapMan = null;
	private static MediaManager mediaMan = null;
	private static ChoiceManager choiceMan = null;
	private static Syncher self = null;

	protected Syncher(Context context) {
		storyMan = StoryManager.getInstance(context);
		chapMan = ChapterManager.getInstance(context);
		mediaMan = MediaManager.getInstance(context);
		choiceMan = ChoiceManager.getInstance(context);
	}

	public static Syncher getInstance(Context context) {
		if (self == null) {
			self = new Syncher(context);
		}
		return self;
	}

	/**
	 *
	 * @param story
	 */
	public void syncStoryFromServer(Story story) {
		ArrayList<UUID> medias = new ArrayList<UUID>();
		storyMan.syncStory(story);

		for (Chapter chap : story.getChapters()) {
			for (Media photo : chap.getPhotos()) {
				medias.add(photo.getId());
				String path = Utilities.saveImageToSD(photo.getBitmapFromString());
				photo.setPath(path);
				mediaMan.syncMedia(photo);
			}
			for (Media ill : chap.getIllustrations()) {
				String path = Utilities.saveImageToSD(ill.getBitmapFromString());
				ill.setPath(path);
				medias.add(ill.getId());
				mediaMan.syncMedia(ill);
			}
			for (Choice choice : chap.getChoices()) {
				choiceMan.syncChoice(choice);
			}
			chapMan.syncChapter(chap);
			mediaMan.syncDeletions(medias, chap.getId());
		}
	}


	public void syncStoryFromMemory(Story story) {
		storyMan.syncStory(story);
		for (Chapter chap : story.getChapters()) {
			syncChapterParts(chap);
			chapMan.syncChapter(chap);
		}
	}	

	public void syncChapterParts(Chapter chap) {
		ArrayList<UUID> medias = new ArrayList<UUID>();

		for (Media photo : chap.getPhotos()) {
			medias.add(photo.getId());
			mediaMan.syncMedia(photo);
		}
		for (Media ill : chap.getIllustrations()) {
			medias.add(ill.getId());
			mediaMan.syncMedia(ill);
		}
		for (Choice choice : chap.getChoices()) {
			choiceMan.syncChoice(choice);
		}
		mediaMan.syncDeletions(medias, chap.getId());
	}		
	
	public void cache(Story story) {
		syncStoryFromServer(story);
	}	
}
