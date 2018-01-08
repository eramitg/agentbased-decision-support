package today.smarthealthcare.decision.experts;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import loadbalancer.experts.KeminiMedian.AgentOpinion;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Lists;

public class KeminiMedianTest {

	@Test
	public void example() {
		List<AgentOpinion<String>> opinions = Lists.newArrayList();
		opinions.add(createExpertOpinion("a3", "a4", "a1", "a5", "a2"));
		opinions.add(createExpertOpinion("a4", "a1", "a3", "a5", "a2"));
		opinions.add(createExpertOpinion("a1", "a5", "a2", "a4", "a3"));
		opinions.add(createExpertOpinion("a1", "a4", "a3", "a5", "a2"));
		opinions.add(createExpertOpinion("a4", "a3", "a1", "a2", "a5"));
		opinions.add(createExpertOpinion("a2", "a4", "a5", "a1", "a3"));

		AgentOpinion<String> best = loadbalancer.experts.KeminiMedian.createMedian(opinions)
				.electBest();
		List<String> range = best.getRange();
		String[] result = { "a4", "a1", "a3", "a5", "a2" };
		assertArrayEquals(result, range.toArray());
	}

	private AgentOpinion<String> createExpertOpinion(String... elements) {
		return AgentOpinion.createOpinion(Lists.newArrayList(elements));
	}

}
