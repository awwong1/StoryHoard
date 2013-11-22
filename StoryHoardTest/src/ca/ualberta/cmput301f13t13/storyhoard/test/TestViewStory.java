/**
 * Copyright 2013 Alex Wong, Ashley Brown, Josh Tate, Kim Wu, Stephanie Gil
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ca.ualberta.cmput301f13t13.storyhoard.test;

import java.util.UUID;

import ca.ualberta.cmput301f13t13.storyhoard.R;
import ca.ualberta.cmput301f13t13.storyhoard.backend.Chapter;
import ca.ualberta.cmput301f13t13.storyhoard.backend.Choice;
import ca.ualberta.cmput301f13t13.storyhoard.backend.LifecycleData;
import ca.ualberta.cmput301f13t13.storyhoard.backend.Story;
import ca.ualberta.cmput301f13t13.storyhoard.gui.ViewStory;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Tests the View Story activity.
 *
 */
public class TestViewStory extends ActivityInstrumentationTestCase2<ViewStory> {
	private ViewStory activity;
	private ImageView storyCover;
	private TextView storyTitle;
	private TextView storyAuthor;
	private TextView storyDescription;
	private Button beginReading;
	private LifecycleData lifedata;
	
	public TestViewStory() {
		super(ViewStory.class);
	}

	/* (non-Javadoc)
	 * @see android.test.ActivityInstrumentationTestCase2#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		lifedata = LifecycleData.getInstance();
		
		Story story = new Story("title", "author", "es", "432432");
		Chapter chapter = new Chapter(story.getId(), "chapter");
		Choice c1 = new Choice(chapter.getId(), UUID.randomUUID(), "c1");
		chapter.addChoice(c1);
		story.addChapter(chapter);
		lifedata.setStory(story);

		activity = getActivity();
	}

	public void testPreConditions() {

		storyCover = (ImageView) activity.findViewById(R.id.storyImage);
		storyTitle = (TextView) activity.findViewById(R.id.storyTitle);
		storyAuthor = (TextView) activity.findViewById(R.id.storyAuthor);
		storyDescription = (TextView) activity.findViewById(R.id.storyDescription);
		beginReading = (Button) activity.findViewById(R.id.viewFirstChapter);		
		
		assertTrue(storyCover != null);
		assertTrue(storyTitle != null);
		assertTrue(storyAuthor != null);
		assertTrue(storyDescription != null);
		assertTrue(beginReading != null);
	}
	
	@UiThreadTest
	public void testSetAuthor() {
		String author = "The Best Author Ever";
		storyAuthor.setText(author);
		assertTrue(storyAuthor.getText().toString().equals(author));
	}	
	
	@UiThreadTest
	public void testSetTitle() {
		String title = "My Title";
		storyTitle.setText(title);
		assertTrue(storyTitle.getText().toString().equals(title));
	}	
	
	@UiThreadTest
	public void testSetDescription() {
		String desc = "This is the story of a new description.";
		storyDescription.setText(desc);
		assertTrue(storyDescription.getText().toString().equals(desc));
	}	
}
