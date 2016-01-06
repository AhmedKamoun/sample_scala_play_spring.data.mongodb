package com.core.dom

import org.springframework.data.annotation.Id


abstract class MainEntity extends Serializable {
  /*
	 * the technical database and object key.
	 */
  @Id
  var id: String = _

}
