package org.oppia.app.testing

import android.os.Bundle
import org.oppia.app.activity.InjectableAppCompatActivity
import org.oppia.app.home.RouteToExplorationListener
import org.oppia.app.player.exploration.ExplorationActivity
import org.oppia.app.story.StoryActivity
import org.oppia.app.topic.RouteToConceptCardListener
import org.oppia.app.topic.RouteToQuestionPlayerListener
import org.oppia.app.topic.RouteToStoryListener
import org.oppia.app.topic.RouteToTopicPlayListener
import org.oppia.app.topic.TOPIC_FRAGMENT_TAG
import org.oppia.app.topic.TopicActivityPresenter
import org.oppia.app.topic.TopicFragment
import org.oppia.app.topic.TopicTab
import org.oppia.app.topic.conceptcard.ConceptCardFragment
import org.oppia.app.topic.questionplayer.QuestionPlayerActivity
import org.oppia.domain.topic.FRACTIONS_TOPIC_ID
import javax.inject.Inject

/** The activity for testing [TopicFragment]. */
class TopicTestActivity : InjectableAppCompatActivity(), RouteToQuestionPlayerListener, RouteToConceptCardListener,
  RouteToTopicPlayListener, RouteToStoryListener, RouteToExplorationListener {
  @Inject
  lateinit var topicActivityPresenter: TopicActivityPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    activityComponent.inject(this)
    topicActivityPresenter.handleOnCreate(topicId = FRACTIONS_TOPIC_ID, storyId = "")
  }

  override fun routeToQuestionPlayer(skillIdList: ArrayList<String>) {
    startActivity(QuestionPlayerActivity.createQuestionPlayerActivityIntent(this, skillIdList))
  }

  override fun routeToStory(storyId: String) {
    startActivity(StoryActivity.createStoryActivityIntent(this, storyId))
  }

  override fun routeToTopicPlayFragment() {
    val topicFragment = supportFragmentManager.findFragmentByTag(TOPIC_FRAGMENT_TAG) as TopicFragment
    topicFragment.topicFragmentPresenter.setCurrentTab(TopicTab.PLAY)
  }

  override fun routeToConceptCard(skillId: String) {
    if (getConceptCardFragment() == null) {
      val conceptCardFragment: ConceptCardFragment = ConceptCardFragment.newInstance(skillId)
      conceptCardFragment.showNow(supportFragmentManager, TAG_CONCEPT_CARD_DIALOG)
    }
  }

  override fun routeToExploration(explorationId: String) {
    startActivity(ExplorationActivity.createExplorationActivityIntent(this, explorationId))
  }

  private fun getConceptCardFragment(): ConceptCardFragment? {
    return supportFragmentManager.findFragmentByTag(TAG_CONCEPT_CARD_DIALOG) as ConceptCardFragment?
  }

  companion object {
    internal const val TAG_CONCEPT_CARD_DIALOG = "CONCEPT_CARD_DIALOG"
  }
}
