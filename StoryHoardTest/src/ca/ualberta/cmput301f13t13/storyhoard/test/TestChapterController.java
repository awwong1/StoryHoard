package ca.ualberta.cmput301f13t13.storyhoard.test;

import java.util.ArrayList;
import java.util.UUID;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cmput301f13t13.storyhoard.controllers.ChapterController;
import ca.ualberta.cmput301f13t13.storyhoard.controllers.ChoiceController;
import ca.ualberta.cmput301f13t13.storyhoard.controllers.MediaController;
import ca.ualberta.cmput301f13t13.storyhoard.dataClasses.Chapter;
import ca.ualberta.cmput301f13t13.storyhoard.dataClasses.Choice;
import ca.ualberta.cmput301f13t13.storyhoard.dataClasses.Media;
import ca.ualberta.cmput301f13t13.storyhoard.gui.ViewBrowseStories;
import ca.ualberta.cmput301f13t13.storyhoard.local.DBContract;
import ca.ualberta.cmput301f13t13.storyhoard.local.DBHelper;
import ca.ualberta.cmput301f13t13.storyhoard.local.StoryManager;

/**
 * @author sgil
 *
 */
public class TestChapterController extends ActivityInstrumentationTestCase2<ViewBrowseStories> {
	private Chapter mockChapter;
	private Chapter mockChapter2;
	private Chapter mockChapter3;
	private ArrayList<Chapter> mockChapters;
	private ChapterController chapCon;
	private ChoiceController choiceCon;
	private MediaController mediaCon;
	
	public TestChapterController() {
		super(ViewBrowseStories.class);
		// TODO Auto-generated constructor stub
	}
	/* (non-Javadoc)
	 * @see android.test.ActivityInstrumentationTestCase2#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		chapCon = ChapterController.getInstance(getActivity());
		choiceCon = ChoiceController.getInstance(getActivity());
		mediaCon = MediaController.getInstance(getActivity());
	}
	
	/**
	 * Tests getting all chapters from a story.
	 */
	public void testGetChaptersByStory() {
		mockChapter = new Chapter(UUID.randomUUID(), "bob went away");
		chapCon.insert(mockChapter);
		mockChapter2 = new Chapter(mockChapter.getStoryId(),
				"Lily drove");
		chapCon.insert(mockChapter2);
		mockChapter3 = new Chapter(UUID.randomUUID(), "Lily drove");
		chapCon.insert(mockChapter3);

		mockChapters = chapCon.getChaptersByStory(mockChapter.getStoryId());
		assertEquals(mockChapters.size(), 2);		
	}
	
	/**
	 * Tests getting all created chapters.
	 */
	public void testGetAll() {
		// Clearing database
		DBHelper helper = DBHelper.getInstance(this.getActivity());
		helper.close();
		this.getActivity().deleteDatabase(DBContract.DATABASE_NAME);
		
		mockChapter = new Chapter(UUID.randomUUID(), "bob went away");
		chapCon.insert(mockChapter);
		mockChapter2 = new Chapter(mockChapter.getStoryId(),
				"Lily drove");
		chapCon.insert(mockChapter2);
		mockChapter3 = new Chapter(UUID.randomUUID(), "Lily drove");
		chapCon.insert(mockChapter3);

		mockChapters = chapCon.getAll();
		assertEquals(mockChapters.size(), 3);		
	}

	/**
	 * Tests getting a full chapter back from the database (including
	 * choices and media).
	 */
	public void testGetFullChapter() {
		mockChapter = new Chapter(UUID.randomUUID(), "bob went away");
		Choice c1 = new Choice(mockChapter.getId(), UUID.randomUUID(), "c1");
		Media m1 = new Media(mockChapter.getId(), null, Media.ILLUSTRATION);
		choiceCon.insert(c1);
		mediaCon.insert(m1);
		chapCon.insert(mockChapter);
		
		Chapter newChapter = chapCon.getFullChapter(mockChapter.getId());
		assertEquals(newChapter.getChoices().size(), 1);
		assertEquals(newChapter.getIllustrations().size(), 1);
		assertTrue(mockChapter.getText().equals(newChapter.getText()));
	}
	
	/**
	 * Tests inserting, retrieving, and updating a chapter.
	 */
	public void testInsertRetrieveUpdate() {
		mockChapter = new Chapter(UUID.randomUUID(), "bob went away");
		chapCon.insert(mockChapter);
		
		mockChapters = chapCon.retrieve(mockChapter);
		assertEquals(mockChapters.size(), 1);
		
		mockChapter2 = mockChapters.get(0);
		mockChapter2.setText("hello");
		chapCon.update(mockChapter2);
		
		mockChapters = chapCon.retrieve(mockChapter);
		assertEquals(mockChapters.size(), 1);	
		mockChapter2 = mockChapters.get(0);
		
		assertFalse(mockChapter2.getText().equals(mockChapter.getText()));
	}
}