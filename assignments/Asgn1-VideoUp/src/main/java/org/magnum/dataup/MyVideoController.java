// Derived from Example 3-VideoControllerAndRetrofit

package org.magnum.dataup;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.magnum.dataup.model.Video;
//import org.magnum.dataup.VideoFileManager;

// import org.magnum.mobilecloud.video.client.VideoSvcApi;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class MyVideoController {

	
	
	// An in-memory list that the servlet uses to store the
	// videos that are sent to it by clients
	private List<Video> videos = new CopyOnWriteArrayList<Video>();
	
	// One way to generate a unique ID for each video is to 
	// use an AtomicLong similar to this:
	
	private static final AtomicLong currentId = new AtomicLong(0L);
	private Map<Long,Video> videos = new HashMap<Long, Video>();

	public Video save(Video entity) {
		checkAndSetId(entity);
		videos.put(entity.getId(), entity);
		return entity;
	}

	private void checkAndSetId(Video entity) {
		if(entity.getId() == 0){
			entity.setId(currentId.incrementAndGet());
		}
	}
	
	// Receives GET requests to /video and returns the current
	// list of videos in memory. Spring automatically converts
	// the list of videos to JSON because of the @ResponseBody
	// annotation.
	@RequestMapping(value=VIDEO_SVC_PATH, method=RequestMethod.GET)
	public @ResponseBody List<Video> getVideoList(){
		return videos.values();
	}
	

	//
    //	VideoFileManager manager = VideoFileManager.get();

	// The VIDEO_SVC_PATH is set to "/video" in the VideoSvcApi
	// interface. We use this constant to ensure that the 
	// client and service paths for the VideoSvc are always
	// in synch.
	public static final String VIDEO_SVC_PATH = "/video";
	
	// Receives POST requests to /video and converts the HTTP
	// request body, which should contain json, into a Video
	// object before adding it to the list. The @RequestBody
	// annotation on the Video parameter is what tells Spring
	// to interpret the HTTP request body as JSON and convert
	// it into a Video object to pass into the method. The
	// @ResponseBody annotation tells Spring to convert the
	// return value from the method back into JSON and put
	// it into the body of the HTTP response to the client.
	@RequestMapping(value=VIDEO_SVC_PATH, method=RequestMethod.POST)
	public @ResponseBody boolean addVideo(@RequestBody Video v){
		return videos.add(v);
	}
	

}