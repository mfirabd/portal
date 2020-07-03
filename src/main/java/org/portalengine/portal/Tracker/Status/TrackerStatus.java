package org.portalengine.portal.Tracker.Status;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.portalengine.portal.Auditable;
import org.portalengine.portal.Tracker.Tracker;

import lombok.Data;

@Data
@Entity
@Table(name = "portal_tracker_status")
public class TrackerStatus extends Auditable<String> {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "tracker_id" )
	private Tracker tracker;
	
	@NotNull
	private String name;
	
	private boolean attachable;
	private boolean updateable;
	
	private String displayFields;

	public TrackerStatus copy(Tracker destTracker) {
		TrackerStatus newstatus = new TrackerStatus();
		newstatus.name = this.name;
		newstatus.attachable = this.attachable;
		newstatus.updateable = this.updateable;
		newstatus.displayFields = this.displayFields;
		newstatus.tracker = destTracker;
		return newstatus;
	}
}