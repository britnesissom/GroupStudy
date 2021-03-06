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
 * on 2015-05-06 at 15:33:56 UTC 
 * Modify at your own risk.
 */

package ee461l.groupstudyendpoints.groupstudyEndpoint.model;

/**
 * Model definition for Groups.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the groupstudyEndpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Groups extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String adminUser;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<FilesEntity> files;

  static {
    // hack to force ProGuard to consider FilesEntity used, since otherwise it would be stripped out
    // see http://code.google.com/p/google-api-java-client/issues/detail?id=528
    com.google.api.client.util.Data.nullOf(FilesEntity.class);
  }

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String groupName;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<java.lang.String> messages;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<java.lang.String> tasks;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<java.lang.String> teammates;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getAdminUser() {
    return adminUser;
  }

  /**
   * @param adminUser adminUser or {@code null} for none
   */
  public Groups setAdminUser(java.lang.String adminUser) {
    this.adminUser = adminUser;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<FilesEntity> getFiles() {
    return files;
  }

  /**
   * @param files files or {@code null} for none
   */
  public Groups setFiles(java.util.List<FilesEntity> files) {
    this.files = files;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getGroupName() {
    return groupName;
  }

  /**
   * @param groupName groupName or {@code null} for none
   */
  public Groups setGroupName(java.lang.String groupName) {
    this.groupName = groupName;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getId() {
    return id;
  }

  /**
   * @param id id or {@code null} for none
   */
  public Groups setId(java.lang.String id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<java.lang.String> getMessages() {
    return messages;
  }

  /**
   * @param messages messages or {@code null} for none
   */
  public Groups setMessages(java.util.List<java.lang.String> messages) {
    this.messages = messages;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<java.lang.String> getTasks() {
    return tasks;
  }

  /**
   * @param tasks tasks or {@code null} for none
   */
  public Groups setTasks(java.util.List<java.lang.String> tasks) {
    this.tasks = tasks;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<java.lang.String> getTeammates() {
    return teammates;
  }

  /**
   * @param teammates teammates or {@code null} for none
   */
  public Groups setTeammates(java.util.List<java.lang.String> teammates) {
    this.teammates = teammates;
    return this;
  }

  @Override
  public Groups set(String fieldName, Object value) {
    return (Groups) super.set(fieldName, value);
  }

  @Override
  public Groups clone() {
    return (Groups) super.clone();
  }

}
