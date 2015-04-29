/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2015-03-26 20:30:19 UTC)
 * on 2015-04-29 at 10:38:43 UTC 
 * Modify at your own risk.
 */

package ee461l.groupstudyendpoints.groupstudyEndpoint.model;

/**
 * Model definition for GroupWrapperEntity.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the groupstudyEndpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class GroupWrapperEntity extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private Groups group;

  /**
   * @return value or {@code null} for none
   */
  public Groups getGroup() {
    return group;
  }

  /**
   * @param group group or {@code null} for none
   */
  public GroupWrapperEntity setGroup(Groups group) {
    this.group = group;
    return this;
  }

  @Override
  public GroupWrapperEntity set(String fieldName, Object value) {
    return (GroupWrapperEntity) super.set(fieldName, value);
  }

  @Override
  public GroupWrapperEntity clone() {
    return (GroupWrapperEntity) super.clone();
  }

}
