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
 * on 2015-05-05 at 23:34:55 UTC 
 * Modify at your own risk.
 */

package ee461l.groupstudyendpoints.groupstudyEndpoint.model;

/**
 * Model definition for FilesEntity.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the groupstudyEndpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class FilesEntity extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String fileContents;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String fileName;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String id;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getFileContents() {
    return fileContents;
  }

  /**
   * @param fileContents fileContents or {@code null} for none
   */
  public FilesEntity setFileContents(java.lang.String fileContents) {
    this.fileContents = fileContents;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getFileName() {
    return fileName;
  }

  /**
   * @param fileName fileName or {@code null} for none
   */
  public FilesEntity setFileName(java.lang.String fileName) {
    this.fileName = fileName;
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
  public FilesEntity setId(java.lang.String id) {
    this.id = id;
    return this;
  }

  @Override
  public FilesEntity set(String fieldName, Object value) {
    return (FilesEntity) super.set(fieldName, value);
  }

  @Override
  public FilesEntity clone() {
    return (FilesEntity) super.clone();
  }

}
