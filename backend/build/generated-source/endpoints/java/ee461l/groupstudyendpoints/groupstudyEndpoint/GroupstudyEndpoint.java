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
 * on 2015-04-28 at 02:34:36 UTC 
 * Modify at your own risk.
 */

package ee461l.groupstudyendpoints.groupstudyEndpoint;

/**
 * Service definition for GroupstudyEndpoint (v1).
 *
 * <p>
 * This is an API
 * </p>
 *
 * <p>
 * For more information about this service, see the
 * <a href="" target="_blank">API Documentation</a>
 * </p>
 *
 * <p>
 * This service uses {@link GroupstudyEndpointRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class GroupstudyEndpoint extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.20.0 of the groupstudyEndpoint library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
  }

  /**
   * The default encoded root URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_ROOT_URL = "https://myApplicationId.appspot.com/_ah/api/";

  /**
   * The default encoded service path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_SERVICE_PATH = "groupstudyEndpoint/v1/";

  /**
   * The default encoded base URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   */
  public static final String DEFAULT_BASE_URL = DEFAULT_ROOT_URL + DEFAULT_SERVICE_PATH;

  /**
   * Constructor.
   *
   * <p>
   * Use {@link Builder} if you need to specify any of the optional parameters.
   * </p>
   *
   * @param transport HTTP transport, which should normally be:
   *        <ul>
   *        <li>Google App Engine:
   *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
   *        <li>Android: {@code newCompatibleTransport} from
   *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
   *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
   *        </li>
   *        </ul>
   * @param jsonFactory JSON factory, which may be:
   *        <ul>
   *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
   *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
   *        <li>Android Honeycomb or higher:
   *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
   *        </ul>
   * @param httpRequestInitializer HTTP request initializer or {@code null} for none
   * @since 1.7
   */
  public GroupstudyEndpoint(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  GroupstudyEndpoint(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "addFile".
   *
   * This request holds the parameters needed by the groupstudyEndpoint server.  After setting any
   * optional parameters, call the {@link AddFile#execute()} method to invoke the remote operation.
   *
   * @param groupName
   * @param file
   * @return the request
   */
  public AddFile addFile(java.lang.String groupName, java.lang.String file) throws java.io.IOException {
    AddFile result = new AddFile(groupName, file);
    initialize(result);
    return result;
  }

  public class AddFile extends GroupstudyEndpointRequest<ee461l.groupstudyendpoints.groupstudyEndpoint.model.Groups> {

    private static final String REST_PATH = "addFile/{groupName}/{file}";

    /**
     * Create a request for the method "addFile".
     *
     * This request holds the parameters needed by the the groupstudyEndpoint server.  After setting
     * any optional parameters, call the {@link AddFile#execute()} method to invoke the remote
     * operation. <p> {@link
     * AddFile#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)} must
     * be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param groupName
     * @param file
     * @since 1.13
     */
    protected AddFile(java.lang.String groupName, java.lang.String file) {
      super(GroupstudyEndpoint.this, "POST", REST_PATH, null, ee461l.groupstudyendpoints.groupstudyEndpoint.model.Groups.class);
      this.groupName = com.google.api.client.util.Preconditions.checkNotNull(groupName, "Required parameter groupName must be specified.");
      this.file = com.google.api.client.util.Preconditions.checkNotNull(file, "Required parameter file must be specified.");
    }

    @Override
    public AddFile setAlt(java.lang.String alt) {
      return (AddFile) super.setAlt(alt);
    }

    @Override
    public AddFile setFields(java.lang.String fields) {
      return (AddFile) super.setFields(fields);
    }

    @Override
    public AddFile setKey(java.lang.String key) {
      return (AddFile) super.setKey(key);
    }

    @Override
    public AddFile setOauthToken(java.lang.String oauthToken) {
      return (AddFile) super.setOauthToken(oauthToken);
    }

    @Override
    public AddFile setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (AddFile) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public AddFile setQuotaUser(java.lang.String quotaUser) {
      return (AddFile) super.setQuotaUser(quotaUser);
    }

    @Override
    public AddFile setUserIp(java.lang.String userIp) {
      return (AddFile) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String groupName;

    /**

     */
    public java.lang.String getGroupName() {
      return groupName;
    }

    public AddFile setGroupName(java.lang.String groupName) {
      this.groupName = groupName;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String file;

    /**

     */
    public java.lang.String getFile() {
      return file;
    }

    public AddFile setFile(java.lang.String file) {
      this.file = file;
      return this;
    }

    @Override
    public AddFile set(String parameterName, Object value) {
      return (AddFile) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "createGroup".
   *
   * This request holds the parameters needed by the groupstudyEndpoint server.  After setting any
   * optional parameters, call the {@link CreateGroup#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link ee461l.groupstudyendpoints.groupstudyEndpoint.model.GroupWrapperEntity}
   * @return the request
   */
  public CreateGroup createGroup(ee461l.groupstudyendpoints.groupstudyEndpoint.model.GroupWrapperEntity content) throws java.io.IOException {
    CreateGroup result = new CreateGroup(content);
    initialize(result);
    return result;
  }

  public class CreateGroup extends GroupstudyEndpointRequest<ee461l.groupstudyendpoints.groupstudyEndpoint.model.Groups> {

    private static final String REST_PATH = "createGroup";

    /**
     * Create a request for the method "createGroup".
     *
     * This request holds the parameters needed by the the groupstudyEndpoint server.  After setting
     * any optional parameters, call the {@link CreateGroup#execute()} method to invoke the remote
     * operation. <p> {@link
     * CreateGroup#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param content the {@link ee461l.groupstudyendpoints.groupstudyEndpoint.model.GroupWrapperEntity}
     * @since 1.13
     */
    protected CreateGroup(ee461l.groupstudyendpoints.groupstudyEndpoint.model.GroupWrapperEntity content) {
      super(GroupstudyEndpoint.this, "POST", REST_PATH, content, ee461l.groupstudyendpoints.groupstudyEndpoint.model.Groups.class);
    }

    @Override
    public CreateGroup setAlt(java.lang.String alt) {
      return (CreateGroup) super.setAlt(alt);
    }

    @Override
    public CreateGroup setFields(java.lang.String fields) {
      return (CreateGroup) super.setFields(fields);
    }

    @Override
    public CreateGroup setKey(java.lang.String key) {
      return (CreateGroup) super.setKey(key);
    }

    @Override
    public CreateGroup setOauthToken(java.lang.String oauthToken) {
      return (CreateGroup) super.setOauthToken(oauthToken);
    }

    @Override
    public CreateGroup setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (CreateGroup) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public CreateGroup setQuotaUser(java.lang.String quotaUser) {
      return (CreateGroup) super.setQuotaUser(quotaUser);
    }

    @Override
    public CreateGroup setUserIp(java.lang.String userIp) {
      return (CreateGroup) super.setUserIp(userIp);
    }

    @Override
    public CreateGroup set(String parameterName, Object value) {
      return (CreateGroup) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "createTask".
   *
   * This request holds the parameters needed by the groupstudyEndpoint server.  After setting any
   * optional parameters, call the {@link CreateTask#execute()} method to invoke the remote operation.
   *
   * @param groupName
   * @param task
   * @return the request
   */
  public CreateTask createTask(java.lang.String groupName, java.lang.String task) throws java.io.IOException {
    CreateTask result = new CreateTask(groupName, task);
    initialize(result);
    return result;
  }

  public class CreateTask extends GroupstudyEndpointRequest<ee461l.groupstudyendpoints.groupstudyEndpoint.model.Groups> {

    private static final String REST_PATH = "createTask/{groupName}/{task}";

    /**
     * Create a request for the method "createTask".
     *
     * This request holds the parameters needed by the the groupstudyEndpoint server.  After setting
     * any optional parameters, call the {@link CreateTask#execute()} method to invoke the remote
     * operation. <p> {@link
     * CreateTask#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param groupName
     * @param task
     * @since 1.13
     */
    protected CreateTask(java.lang.String groupName, java.lang.String task) {
      super(GroupstudyEndpoint.this, "POST", REST_PATH, null, ee461l.groupstudyendpoints.groupstudyEndpoint.model.Groups.class);
      this.groupName = com.google.api.client.util.Preconditions.checkNotNull(groupName, "Required parameter groupName must be specified.");
      this.task = com.google.api.client.util.Preconditions.checkNotNull(task, "Required parameter task must be specified.");
    }

    @Override
    public CreateTask setAlt(java.lang.String alt) {
      return (CreateTask) super.setAlt(alt);
    }

    @Override
    public CreateTask setFields(java.lang.String fields) {
      return (CreateTask) super.setFields(fields);
    }

    @Override
    public CreateTask setKey(java.lang.String key) {
      return (CreateTask) super.setKey(key);
    }

    @Override
    public CreateTask setOauthToken(java.lang.String oauthToken) {
      return (CreateTask) super.setOauthToken(oauthToken);
    }

    @Override
    public CreateTask setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (CreateTask) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public CreateTask setQuotaUser(java.lang.String quotaUser) {
      return (CreateTask) super.setQuotaUser(quotaUser);
    }

    @Override
    public CreateTask setUserIp(java.lang.String userIp) {
      return (CreateTask) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String groupName;

    /**

     */
    public java.lang.String getGroupName() {
      return groupName;
    }

    public CreateTask setGroupName(java.lang.String groupName) {
      this.groupName = groupName;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String task;

    /**

     */
    public java.lang.String getTask() {
      return task;
    }

    public CreateTask setTask(java.lang.String task) {
      this.task = task;
      return this;
    }

    @Override
    public CreateTask set(String parameterName, Object value) {
      return (CreateTask) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "createUser".
   *
   * This request holds the parameters needed by the groupstudyEndpoint server.  After setting any
   * optional parameters, call the {@link CreateUser#execute()} method to invoke the remote operation.
   *
   * @param name
   * @param password
   * @return the request
   */
  public CreateUser createUser(java.lang.String name, java.lang.String password) throws java.io.IOException {
    CreateUser result = new CreateUser(name, password);
    initialize(result);
    return result;
  }

  public class CreateUser extends GroupstudyEndpointRequest<ee461l.groupstudyendpoints.groupstudyEndpoint.model.User> {

    private static final String REST_PATH = "createUser/{name}/{password}";

    /**
     * Create a request for the method "createUser".
     *
     * This request holds the parameters needed by the the groupstudyEndpoint server.  After setting
     * any optional parameters, call the {@link CreateUser#execute()} method to invoke the remote
     * operation. <p> {@link
     * CreateUser#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param name
     * @param password
     * @since 1.13
     */
    protected CreateUser(java.lang.String name, java.lang.String password) {
      super(GroupstudyEndpoint.this, "POST", REST_PATH, null, ee461l.groupstudyendpoints.groupstudyEndpoint.model.User.class);
      this.name = com.google.api.client.util.Preconditions.checkNotNull(name, "Required parameter name must be specified.");
      this.password = com.google.api.client.util.Preconditions.checkNotNull(password, "Required parameter password must be specified.");
    }

    @Override
    public CreateUser setAlt(java.lang.String alt) {
      return (CreateUser) super.setAlt(alt);
    }

    @Override
    public CreateUser setFields(java.lang.String fields) {
      return (CreateUser) super.setFields(fields);
    }

    @Override
    public CreateUser setKey(java.lang.String key) {
      return (CreateUser) super.setKey(key);
    }

    @Override
    public CreateUser setOauthToken(java.lang.String oauthToken) {
      return (CreateUser) super.setOauthToken(oauthToken);
    }

    @Override
    public CreateUser setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (CreateUser) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public CreateUser setQuotaUser(java.lang.String quotaUser) {
      return (CreateUser) super.setQuotaUser(quotaUser);
    }

    @Override
    public CreateUser setUserIp(java.lang.String userIp) {
      return (CreateUser) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String name;

    /**

     */
    public java.lang.String getName() {
      return name;
    }

    public CreateUser setName(java.lang.String name) {
      this.name = name;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String password;

    /**

     */
    public java.lang.String getPassword() {
      return password;
    }

    public CreateUser setPassword(java.lang.String password) {
      this.password = password;
      return this;
    }

    @Override
    public CreateUser set(String parameterName, Object value) {
      return (CreateUser) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "loadGroups".
   *
   * This request holds the parameters needed by the groupstudyEndpoint server.  After setting any
   * optional parameters, call the {@link LoadGroups#execute()} method to invoke the remote operation.
   *
   * @return the request
   */
  public LoadGroups loadGroups() throws java.io.IOException {
    LoadGroups result = new LoadGroups();
    initialize(result);
    return result;
  }

  public class LoadGroups extends GroupstudyEndpointRequest<ee461l.groupstudyendpoints.groupstudyEndpoint.model.CollectionResponseGroups> {

    private static final String REST_PATH = "loadGroups";

    /**
     * Create a request for the method "loadGroups".
     *
     * This request holds the parameters needed by the the groupstudyEndpoint server.  After setting
     * any optional parameters, call the {@link LoadGroups#execute()} method to invoke the remote
     * operation. <p> {@link
     * LoadGroups#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected LoadGroups() {
      super(GroupstudyEndpoint.this, "POST", REST_PATH, null, ee461l.groupstudyendpoints.groupstudyEndpoint.model.CollectionResponseGroups.class);
    }

    @Override
    public LoadGroups setAlt(java.lang.String alt) {
      return (LoadGroups) super.setAlt(alt);
    }

    @Override
    public LoadGroups setFields(java.lang.String fields) {
      return (LoadGroups) super.setFields(fields);
    }

    @Override
    public LoadGroups setKey(java.lang.String key) {
      return (LoadGroups) super.setKey(key);
    }

    @Override
    public LoadGroups setOauthToken(java.lang.String oauthToken) {
      return (LoadGroups) super.setOauthToken(oauthToken);
    }

    @Override
    public LoadGroups setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (LoadGroups) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public LoadGroups setQuotaUser(java.lang.String quotaUser) {
      return (LoadGroups) super.setQuotaUser(quotaUser);
    }

    @Override
    public LoadGroups setUserIp(java.lang.String userIp) {
      return (LoadGroups) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Integer count;

    /**

     */
    public java.lang.Integer getCount() {
      return count;
    }

    public LoadGroups setCount(java.lang.Integer count) {
      this.count = count;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String cursor;

    /**

     */
    public java.lang.String getCursor() {
      return cursor;
    }

    public LoadGroups setCursor(java.lang.String cursor) {
      this.cursor = cursor;
      return this;
    }

    @Override
    public LoadGroups set(String parameterName, Object value) {
      return (LoadGroups) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "loadUsers".
   *
   * This request holds the parameters needed by the groupstudyEndpoint server.  After setting any
   * optional parameters, call the {@link LoadUsers#execute()} method to invoke the remote operation.
   *
   * @return the request
   */
  public LoadUsers loadUsers() throws java.io.IOException {
    LoadUsers result = new LoadUsers();
    initialize(result);
    return result;
  }

  public class LoadUsers extends GroupstudyEndpointRequest<ee461l.groupstudyendpoints.groupstudyEndpoint.model.CollectionResponseUser> {

    private static final String REST_PATH = "loadUsers";

    /**
     * Create a request for the method "loadUsers".
     *
     * This request holds the parameters needed by the the groupstudyEndpoint server.  After setting
     * any optional parameters, call the {@link LoadUsers#execute()} method to invoke the remote
     * operation. <p> {@link
     * LoadUsers#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected LoadUsers() {
      super(GroupstudyEndpoint.this, "POST", REST_PATH, null, ee461l.groupstudyendpoints.groupstudyEndpoint.model.CollectionResponseUser.class);
    }

    @Override
    public LoadUsers setAlt(java.lang.String alt) {
      return (LoadUsers) super.setAlt(alt);
    }

    @Override
    public LoadUsers setFields(java.lang.String fields) {
      return (LoadUsers) super.setFields(fields);
    }

    @Override
    public LoadUsers setKey(java.lang.String key) {
      return (LoadUsers) super.setKey(key);
    }

    @Override
    public LoadUsers setOauthToken(java.lang.String oauthToken) {
      return (LoadUsers) super.setOauthToken(oauthToken);
    }

    @Override
    public LoadUsers setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (LoadUsers) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public LoadUsers setQuotaUser(java.lang.String quotaUser) {
      return (LoadUsers) super.setQuotaUser(quotaUser);
    }

    @Override
    public LoadUsers setUserIp(java.lang.String userIp) {
      return (LoadUsers) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Integer count;

    /**

     */
    public java.lang.Integer getCount() {
      return count;
    }

    public LoadUsers setCount(java.lang.Integer count) {
      this.count = count;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String cursor;

    /**

     */
    public java.lang.String getCursor() {
      return cursor;
    }

    public LoadUsers setCursor(java.lang.String cursor) {
      this.cursor = cursor;
      return this;
    }

    @Override
    public LoadUsers set(String parameterName, Object value) {
      return (LoadUsers) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "retrieveSingleGroup".
   *
   * This request holds the parameters needed by the groupstudyEndpoint server.  After setting any
   * optional parameters, call the {@link RetrieveSingleGroup#execute()} method to invoke the remote
   * operation.
   *
   * @param groupName
   * @return the request
   */
  public RetrieveSingleGroup retrieveSingleGroup(java.lang.String groupName) throws java.io.IOException {
    RetrieveSingleGroup result = new RetrieveSingleGroup(groupName);
    initialize(result);
    return result;
  }

  public class RetrieveSingleGroup extends GroupstudyEndpointRequest<ee461l.groupstudyendpoints.groupstudyEndpoint.model.Groups> {

    private static final String REST_PATH = "retrieveSingleGroup/{groupName}";

    /**
     * Create a request for the method "retrieveSingleGroup".
     *
     * This request holds the parameters needed by the the groupstudyEndpoint server.  After setting
     * any optional parameters, call the {@link RetrieveSingleGroup#execute()} method to invoke the
     * remote operation. <p> {@link RetrieveSingleGroup#initialize(com.google.api.client.googleapis.se
     * rvices.AbstractGoogleClientRequest)} must be called to initialize this instance immediately
     * after invoking the constructor. </p>
     *
     * @param groupName
     * @since 1.13
     */
    protected RetrieveSingleGroup(java.lang.String groupName) {
      super(GroupstudyEndpoint.this, "POST", REST_PATH, null, ee461l.groupstudyendpoints.groupstudyEndpoint.model.Groups.class);
      this.groupName = com.google.api.client.util.Preconditions.checkNotNull(groupName, "Required parameter groupName must be specified.");
    }

    @Override
    public RetrieveSingleGroup setAlt(java.lang.String alt) {
      return (RetrieveSingleGroup) super.setAlt(alt);
    }

    @Override
    public RetrieveSingleGroup setFields(java.lang.String fields) {
      return (RetrieveSingleGroup) super.setFields(fields);
    }

    @Override
    public RetrieveSingleGroup setKey(java.lang.String key) {
      return (RetrieveSingleGroup) super.setKey(key);
    }

    @Override
    public RetrieveSingleGroup setOauthToken(java.lang.String oauthToken) {
      return (RetrieveSingleGroup) super.setOauthToken(oauthToken);
    }

    @Override
    public RetrieveSingleGroup setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (RetrieveSingleGroup) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public RetrieveSingleGroup setQuotaUser(java.lang.String quotaUser) {
      return (RetrieveSingleGroup) super.setQuotaUser(quotaUser);
    }

    @Override
    public RetrieveSingleGroup setUserIp(java.lang.String userIp) {
      return (RetrieveSingleGroup) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String groupName;

    /**

     */
    public java.lang.String getGroupName() {
      return groupName;
    }

    public RetrieveSingleGroup setGroupName(java.lang.String groupName) {
      this.groupName = groupName;
      return this;
    }

    @Override
    public RetrieveSingleGroup set(String parameterName, Object value) {
      return (RetrieveSingleGroup) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "retrieveSingleUser".
   *
   * This request holds the parameters needed by the groupstudyEndpoint server.  After setting any
   * optional parameters, call the {@link RetrieveSingleUser#execute()} method to invoke the remote
   * operation.
   *
   * @param username
   * @return the request
   */
  public RetrieveSingleUser retrieveSingleUser(java.lang.String username) throws java.io.IOException {
    RetrieveSingleUser result = new RetrieveSingleUser(username);
    initialize(result);
    return result;
  }

  public class RetrieveSingleUser extends GroupstudyEndpointRequest<ee461l.groupstudyendpoints.groupstudyEndpoint.model.User> {

    private static final String REST_PATH = "retrieveSingleUser/{username}";

    /**
     * Create a request for the method "retrieveSingleUser".
     *
     * This request holds the parameters needed by the the groupstudyEndpoint server.  After setting
     * any optional parameters, call the {@link RetrieveSingleUser#execute()} method to invoke the
     * remote operation. <p> {@link RetrieveSingleUser#initialize(com.google.api.client.googleapis.ser
     * vices.AbstractGoogleClientRequest)} must be called to initialize this instance immediately
     * after invoking the constructor. </p>
     *
     * @param username
     * @since 1.13
     */
    protected RetrieveSingleUser(java.lang.String username) {
      super(GroupstudyEndpoint.this, "POST", REST_PATH, null, ee461l.groupstudyendpoints.groupstudyEndpoint.model.User.class);
      this.username = com.google.api.client.util.Preconditions.checkNotNull(username, "Required parameter username must be specified.");
    }

    @Override
    public RetrieveSingleUser setAlt(java.lang.String alt) {
      return (RetrieveSingleUser) super.setAlt(alt);
    }

    @Override
    public RetrieveSingleUser setFields(java.lang.String fields) {
      return (RetrieveSingleUser) super.setFields(fields);
    }

    @Override
    public RetrieveSingleUser setKey(java.lang.String key) {
      return (RetrieveSingleUser) super.setKey(key);
    }

    @Override
    public RetrieveSingleUser setOauthToken(java.lang.String oauthToken) {
      return (RetrieveSingleUser) super.setOauthToken(oauthToken);
    }

    @Override
    public RetrieveSingleUser setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (RetrieveSingleUser) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public RetrieveSingleUser setQuotaUser(java.lang.String quotaUser) {
      return (RetrieveSingleUser) super.setQuotaUser(quotaUser);
    }

    @Override
    public RetrieveSingleUser setUserIp(java.lang.String userIp) {
      return (RetrieveSingleUser) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String username;

    /**

     */
    public java.lang.String getUsername() {
      return username;
    }

    public RetrieveSingleUser setUsername(java.lang.String username) {
      this.username = username;
      return this;
    }

    @Override
    public RetrieveSingleUser set(String parameterName, Object value) {
      return (RetrieveSingleUser) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "updateUsersGroups".
   *
   * This request holds the parameters needed by the groupstudyEndpoint server.  After setting any
   * optional parameters, call the {@link UpdateUsersGroups#execute()} method to invoke the remote
   * operation.
   *
   * @param adminUsername
   * @param content the {@link ee461l.groupstudyendpoints.groupstudyEndpoint.model.Groups}
   * @return the request
   */
  public UpdateUsersGroups updateUsersGroups(java.lang.String adminUsername, ee461l.groupstudyendpoints.groupstudyEndpoint.model.Groups content) throws java.io.IOException {
    UpdateUsersGroups result = new UpdateUsersGroups(adminUsername, content);
    initialize(result);
    return result;
  }

  public class UpdateUsersGroups extends GroupstudyEndpointRequest<ee461l.groupstudyendpoints.groupstudyEndpoint.model.User> {

    private static final String REST_PATH = "user/{adminUsername}";

    /**
     * Create a request for the method "updateUsersGroups".
     *
     * This request holds the parameters needed by the the groupstudyEndpoint server.  After setting
     * any optional parameters, call the {@link UpdateUsersGroups#execute()} method to invoke the
     * remote operation. <p> {@link UpdateUsersGroups#initialize(com.google.api.client.googleapis.serv
     * ices.AbstractGoogleClientRequest)} must be called to initialize this instance immediately after
     * invoking the constructor. </p>
     *
     * @param adminUsername
     * @param content the {@link ee461l.groupstudyendpoints.groupstudyEndpoint.model.Groups}
     * @since 1.13
     */
    protected UpdateUsersGroups(java.lang.String adminUsername, ee461l.groupstudyendpoints.groupstudyEndpoint.model.Groups content) {
      super(GroupstudyEndpoint.this, "PUT", REST_PATH, content, ee461l.groupstudyendpoints.groupstudyEndpoint.model.User.class);
      this.adminUsername = com.google.api.client.util.Preconditions.checkNotNull(adminUsername, "Required parameter adminUsername must be specified.");
    }

    @Override
    public UpdateUsersGroups setAlt(java.lang.String alt) {
      return (UpdateUsersGroups) super.setAlt(alt);
    }

    @Override
    public UpdateUsersGroups setFields(java.lang.String fields) {
      return (UpdateUsersGroups) super.setFields(fields);
    }

    @Override
    public UpdateUsersGroups setKey(java.lang.String key) {
      return (UpdateUsersGroups) super.setKey(key);
    }

    @Override
    public UpdateUsersGroups setOauthToken(java.lang.String oauthToken) {
      return (UpdateUsersGroups) super.setOauthToken(oauthToken);
    }

    @Override
    public UpdateUsersGroups setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (UpdateUsersGroups) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public UpdateUsersGroups setQuotaUser(java.lang.String quotaUser) {
      return (UpdateUsersGroups) super.setQuotaUser(quotaUser);
    }

    @Override
    public UpdateUsersGroups setUserIp(java.lang.String userIp) {
      return (UpdateUsersGroups) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String adminUsername;

    /**

     */
    public java.lang.String getAdminUsername() {
      return adminUsername;
    }

    public UpdateUsersGroups setAdminUsername(java.lang.String adminUsername) {
      this.adminUsername = adminUsername;
      return this;
    }

    @Override
    public UpdateUsersGroups set(String parameterName, Object value) {
      return (UpdateUsersGroups) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link GroupstudyEndpoint}.
   *
   * <p>
   * Implementation is not thread-safe.
   * </p>
   *
   * @since 1.3.0
   */
  public static final class Builder extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder {

    /**
     * Returns an instance of a new builder.
     *
     * @param transport HTTP transport, which should normally be:
     *        <ul>
     *        <li>Google App Engine:
     *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
     *        <li>Android: {@code newCompatibleTransport} from
     *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
     *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
     *        </li>
     *        </ul>
     * @param jsonFactory JSON factory, which may be:
     *        <ul>
     *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
     *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
     *        <li>Android Honeycomb or higher:
     *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
     *        </ul>
     * @param httpRequestInitializer HTTP request initializer or {@code null} for none
     * @since 1.7
     */
    public Builder(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
        com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      super(
          transport,
          jsonFactory,
          DEFAULT_ROOT_URL,
          DEFAULT_SERVICE_PATH,
          httpRequestInitializer,
          false);
    }

    /** Builds a new instance of {@link GroupstudyEndpoint}. */
    @Override
    public GroupstudyEndpoint build() {
      return new GroupstudyEndpoint(this);
    }

    @Override
    public Builder setRootUrl(String rootUrl) {
      return (Builder) super.setRootUrl(rootUrl);
    }

    @Override
    public Builder setServicePath(String servicePath) {
      return (Builder) super.setServicePath(servicePath);
    }

    @Override
    public Builder setHttpRequestInitializer(com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
    }

    @Override
    public Builder setApplicationName(String applicationName) {
      return (Builder) super.setApplicationName(applicationName);
    }

    @Override
    public Builder setSuppressPatternChecks(boolean suppressPatternChecks) {
      return (Builder) super.setSuppressPatternChecks(suppressPatternChecks);
    }

    @Override
    public Builder setSuppressRequiredParameterChecks(boolean suppressRequiredParameterChecks) {
      return (Builder) super.setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
    }

    @Override
    public Builder setSuppressAllChecks(boolean suppressAllChecks) {
      return (Builder) super.setSuppressAllChecks(suppressAllChecks);
    }

    /**
     * Set the {@link GroupstudyEndpointRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setGroupstudyEndpointRequestInitializer(
        GroupstudyEndpointRequestInitializer groupstudyendpointRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(groupstudyendpointRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
