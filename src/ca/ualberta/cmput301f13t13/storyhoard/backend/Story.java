/**
 * Copyright 2013 Alex Wong, Ashley Brown, Josh Tate, Kim Wu, Stephanie Gil
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package ca.ualberta.cmput301f13t13.storyhoard.backend;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import android.content.Context;

/**
 * Role: A container to hold story information. This includes id, author, 
 * title, description, the id of the first chapter, a collection of
 * chapters belonging to it, the id of the phone it was created on. Note 
 * that all id's are stored as UUID's.
 * 
 * @author Stephanie Gil
 * @author Ashley Brown
 */
public class Story {
	private UUID id;
	private String author;
	private String title;
	private String description;
	private UUID firstChapterId;
	private HashMap<UUID, Chapter> chapters;
	private String phoneId;

	/**
	 * Initializes a new story object without need an id as an argument.
	 * 
	 * @param title
	 *            Story title
	 * @param author
	 *            Story author
	 * @param description
	 *            Story description
	 * @param phoneId
	 *            The android id of the phone the story was made on
	 */
	public Story(String title, String author, String description, 
			      String phoneId) {
		this.id = UUID.randomUUID();
		if (author != null) {
			this.author = author.trim();
		}
		if (title != null) {
			this.title = title.trim();
		}
		if (description != null) {
			this.description = description.trim();
		}
		this.phoneId = phoneId;
		this.firstChapterId = null;
		chapters = new HashMap<UUID, Chapter>();
	}

	/**
	 * Initializes a new story object with an id. Usually used when making a
	 * story object that will be holding search criteria since you can
	 * specify the id of the story.
	 * 
	 * @param id
	 * 			  Story's unique id.
	 * @param title
	 *            Story title
	 * @param author
	 *            Story author
	 * @param description
	 *            Story description
	 * @param phoneId
	 *            The android Id of the phone the story was made on
	 */
	public Story(UUID id, String title, String author, String description,
			      String phoneId) {
		this.id = id;
		this.author = author;
		this.title = title;
		this.description = description;
		this.phoneId = phoneId;
		this.firstChapterId = null;
		chapters = new HashMap<UUID, Chapter>();
	}

	/**
	 * Initializes a new story object from data taken from the database.
	 * 
	 * @param id
	 * 			  Story's unique id.
	 * @param title
	 *            Story title
	 * @param author
	 *            Story author
	 * @param description
	 *            Story description
	 * @param chapterId
	 * @param phoneId
	 *            The android id of the phone the story was made on
	 */
	protected Story(String id, String title, String author, String description,
			String chapterId, String phoneId) {
		this.id = UUID.fromString(id);
		this.author = author;
		this.title = title;
		this.description = description;
		if (chapterId != null) {
			this.firstChapterId = UUID.fromString(chapterId);
		}
		this.chapters = new HashMap<UUID, Chapter>();
		this.phoneId = phoneId;
	}

	// GETTERS

	/**
	 * Returns the Id of the story.
	 * 
	 * @return id
	 */
	public UUID getId() {
		return this.id;
	}

	/**
	 * Returns the title of the story.
	 * 
	 * @return title
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Returns the author of the story.
	 * 
	 * @return author
	 */
	public String getAuthor() {
		return this.author;
	}

	/**
	 * Returns the description of the story.
	 * 
	 * @return description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Returns the chapters of the story.
	 * 
	 * @return chapters
	 */
	public HashMap<UUID, Chapter> getChapters() {
		return this.chapters;
	}

	/**
	 * Returns the first chapter of the story.
	 * 
	 * @return firstChapter
	 */
	public UUID getFirstChapterId() {
		return this.firstChapterId;
	}

	/**
	 * Returns the chapter matching the chapter id
	 * 
	 * @return chapter
	 */
	public Chapter getChapter(UUID id) {
		Chapter chap = chapters.get(id);
		return chap;
	}

	/**
	 * Return the android id of the phone that made this story
	 * 
	 * @return phone id
	 */
	public String getPhoneId() {
		return this.phoneId;
	}

	// SETTERS

	/**
	 * Set the Id of the story.
	 * 
	 * @param id
	 */
	public void setId(UUID id) {
		this.id = id;
	}

	/**
	 * Set the title of the story.
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title.trim();
	}

	/**
	 * Set the author of the story.
	 * 
	 * @param author
	 */
	public void setAuthor(String author) {
		this.author = author.trim();
	}

	/**
	 * Set the description of the story.
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description.trim();
	}

	/**
	 * Sets the first chapter of the story.
	 * 
	 * @param chapterId
	 */
	public void setFirstChapterId(UUID chapterId) {
		firstChapterId = chapterId;
	}

	/**
	 * Set the chapters of the story.
	 * 
	 * @param chapters
	 */
	public void setChapters(HashMap<UUID, Chapter> chapters) {
		this.chapters = chapters;
	}

	/**
	 * Sets the string of the phone id
	 * 
	 * @param phoneId
	 */
	public void setPhoneId(String phoneId) {
		this.phoneId = phoneId;
	}

	// Other methods

	/**
	 * Adds a chapter onto the story object. If the story
	 * has no chapters when the new chapter is added, the
	 * firstChapterId will also be set for the story.
	 * 
	 * If the story needs to be updated in the database
	 * (due to now having a firstChapterId), then it will
	 * return true so the front end will know to use the
	 * OwnStoryManager to update the story data.
	 * 
	 * If the story already had its firstChapterId set, then
	 * there is no need to update it in the database, therefore
	 * the function will return False.
	 */
	public Boolean addChapter(Chapter chapter) {
		if (firstChapterId == null) {
			// set first chapter id
			firstChapterId = chapter.getId();
			chapters.put(chapter.getId(), chapter);
			return true;
		}
		chapters.put(chapter.getId(), chapter);
		return false;
	}

	public void updateSelf(Context context) {
		if (phoneId.equals(Utilities.getPhoneId(context))) {
			OwnStoryManager osm = OwnStoryManager.getInstance(context);
			osm.update(this);
		} else {
			CachedStoryManager csm = CachedStoryManager.getInstance(context);
			csm.update(this);
		}
		
		// updating all its chapters
		for (Chapter chap : chapters.values()) {
			chap.updateSelf(context);	
		}
	}
	
	public void addSelf(Context context) {
		if (phoneId.equals(Utilities.getPhoneId(context))) {
			OwnStoryManager osm = OwnStoryManager.getInstance(context);
			osm.insert(this);
		} else {
			CachedStoryManager csm = CachedStoryManager.getInstance(context);
			csm.insert(this);
		}
		
		// adding all its chapters
		for (Chapter chap : chapters.values()) {
			chap.addSelf(context);	
		}		
	}
	
	/**
	 * Returns the information of the story (id, title, author, PhoneId) that
	 * could be used in searching for a story in the database. This information
	 * is returned in a HashMap where the keys are the corresponding Story 
	 * Table column names.
	 * 
	 * @return HashMap
	 */
	public HashMap<String, String> getSearchCriteria() {
		HashMap<String, String> info = new HashMap<String, String>();

		if (id != null) {
			info.put(DBContract.COLUMN_NAME_STORY_ID, id.toString());
		}

		if (title != null) {
			multiWord(info);
		}

		if (phoneId != null) {
			info.put(DBContract.COLUMN_NAME_PHONE_ID, phoneId);
		}

		return info;
	}

	/**
	 * Splits up the string for the title into keywords so a search
	 * to find stories with titles containing the keywords will
	 * be possible.
	 * 
	 * @param info
	 */
	private void multiWord(HashMap<String, String> info) {
		List<String> words;

		words = Arrays.asList(title.split("\\s+"));
		
		for (String keyword : words) {
			info.put(DBContract.COLUMN_NAME_TITLE, "%" + keyword + "%");
		}
	}
}
