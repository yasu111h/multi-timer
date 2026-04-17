package com.multitimer.app.data.db;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.multitimer.app.data.db.dao.LapDao;
import com.multitimer.app.data.db.dao.LapDao_Impl;
import com.multitimer.app.data.db.dao.PresetDao;
import com.multitimer.app.data.db.dao.PresetDao_Impl;
import com.multitimer.app.data.db.dao.StopwatchDao;
import com.multitimer.app.data.db.dao.StopwatchDao_Impl;
import com.multitimer.app.data.db.dao.TimerDao;
import com.multitimer.app.data.db.dao.TimerDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile TimerDao _timerDao;

  private volatile StopwatchDao _stopwatchDao;

  private volatile LapDao _lapDao;

  private volatile PresetDao _presetDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `timers` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `label` TEXT NOT NULL, `total_millis` INTEGER NOT NULL, `remaining_millis` INTEGER NOT NULL, `status` TEXT NOT NULL, `started_at` INTEGER, `paused_at` INTEGER, `sort_order` INTEGER NOT NULL, `created_at` INTEGER NOT NULL, `updated_at` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `stopwatches` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `label` TEXT NOT NULL, `elapsed_millis` INTEGER NOT NULL, `status` TEXT NOT NULL, `started_at` INTEGER, `paused_at` INTEGER, `current_lap_start_millis` INTEGER NOT NULL, `lap_count` INTEGER NOT NULL, `sort_order` INTEGER NOT NULL, `created_at` INTEGER NOT NULL, `updated_at` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `laps` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `stopwatch_id` INTEGER NOT NULL, `lap_number` INTEGER NOT NULL, `lap_millis` INTEGER NOT NULL, `cumulative_millis` INTEGER NOT NULL, `recorded_at` INTEGER NOT NULL, FOREIGN KEY(`stopwatch_id`) REFERENCES `stopwatches`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_laps_stopwatch_id` ON `laps` (`stopwatch_id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `presets` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `label` TEXT NOT NULL, `total_millis` INTEGER NOT NULL, `sort_order` INTEGER NOT NULL, `created_at` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '19c3fb3725b4995250656749d0f61bf3')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `timers`");
        db.execSQL("DROP TABLE IF EXISTS `stopwatches`");
        db.execSQL("DROP TABLE IF EXISTS `laps`");
        db.execSQL("DROP TABLE IF EXISTS `presets`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsTimers = new HashMap<String, TableInfo.Column>(10);
        _columnsTimers.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTimers.put("label", new TableInfo.Column("label", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTimers.put("total_millis", new TableInfo.Column("total_millis", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTimers.put("remaining_millis", new TableInfo.Column("remaining_millis", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTimers.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTimers.put("started_at", new TableInfo.Column("started_at", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTimers.put("paused_at", new TableInfo.Column("paused_at", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTimers.put("sort_order", new TableInfo.Column("sort_order", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTimers.put("created_at", new TableInfo.Column("created_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTimers.put("updated_at", new TableInfo.Column("updated_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTimers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTimers = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTimers = new TableInfo("timers", _columnsTimers, _foreignKeysTimers, _indicesTimers);
        final TableInfo _existingTimers = TableInfo.read(db, "timers");
        if (!_infoTimers.equals(_existingTimers)) {
          return new RoomOpenHelper.ValidationResult(false, "timers(com.multitimer.app.data.db.entity.TimerEntity).\n"
                  + " Expected:\n" + _infoTimers + "\n"
                  + " Found:\n" + _existingTimers);
        }
        final HashMap<String, TableInfo.Column> _columnsStopwatches = new HashMap<String, TableInfo.Column>(11);
        _columnsStopwatches.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStopwatches.put("label", new TableInfo.Column("label", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStopwatches.put("elapsed_millis", new TableInfo.Column("elapsed_millis", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStopwatches.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStopwatches.put("started_at", new TableInfo.Column("started_at", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStopwatches.put("paused_at", new TableInfo.Column("paused_at", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStopwatches.put("current_lap_start_millis", new TableInfo.Column("current_lap_start_millis", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStopwatches.put("lap_count", new TableInfo.Column("lap_count", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStopwatches.put("sort_order", new TableInfo.Column("sort_order", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStopwatches.put("created_at", new TableInfo.Column("created_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStopwatches.put("updated_at", new TableInfo.Column("updated_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysStopwatches = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesStopwatches = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoStopwatches = new TableInfo("stopwatches", _columnsStopwatches, _foreignKeysStopwatches, _indicesStopwatches);
        final TableInfo _existingStopwatches = TableInfo.read(db, "stopwatches");
        if (!_infoStopwatches.equals(_existingStopwatches)) {
          return new RoomOpenHelper.ValidationResult(false, "stopwatches(com.multitimer.app.data.db.entity.StopwatchEntity).\n"
                  + " Expected:\n" + _infoStopwatches + "\n"
                  + " Found:\n" + _existingStopwatches);
        }
        final HashMap<String, TableInfo.Column> _columnsLaps = new HashMap<String, TableInfo.Column>(6);
        _columnsLaps.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLaps.put("stopwatch_id", new TableInfo.Column("stopwatch_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLaps.put("lap_number", new TableInfo.Column("lap_number", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLaps.put("lap_millis", new TableInfo.Column("lap_millis", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLaps.put("cumulative_millis", new TableInfo.Column("cumulative_millis", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLaps.put("recorded_at", new TableInfo.Column("recorded_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysLaps = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysLaps.add(new TableInfo.ForeignKey("stopwatches", "CASCADE", "NO ACTION", Arrays.asList("stopwatch_id"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesLaps = new HashSet<TableInfo.Index>(1);
        _indicesLaps.add(new TableInfo.Index("index_laps_stopwatch_id", false, Arrays.asList("stopwatch_id"), Arrays.asList("ASC")));
        final TableInfo _infoLaps = new TableInfo("laps", _columnsLaps, _foreignKeysLaps, _indicesLaps);
        final TableInfo _existingLaps = TableInfo.read(db, "laps");
        if (!_infoLaps.equals(_existingLaps)) {
          return new RoomOpenHelper.ValidationResult(false, "laps(com.multitimer.app.data.db.entity.LapEntity).\n"
                  + " Expected:\n" + _infoLaps + "\n"
                  + " Found:\n" + _existingLaps);
        }
        final HashMap<String, TableInfo.Column> _columnsPresets = new HashMap<String, TableInfo.Column>(5);
        _columnsPresets.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPresets.put("label", new TableInfo.Column("label", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPresets.put("total_millis", new TableInfo.Column("total_millis", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPresets.put("sort_order", new TableInfo.Column("sort_order", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPresets.put("created_at", new TableInfo.Column("created_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPresets = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPresets = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPresets = new TableInfo("presets", _columnsPresets, _foreignKeysPresets, _indicesPresets);
        final TableInfo _existingPresets = TableInfo.read(db, "presets");
        if (!_infoPresets.equals(_existingPresets)) {
          return new RoomOpenHelper.ValidationResult(false, "presets(com.multitimer.app.data.db.entity.PresetEntity).\n"
                  + " Expected:\n" + _infoPresets + "\n"
                  + " Found:\n" + _existingPresets);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "19c3fb3725b4995250656749d0f61bf3", "b3f2779b81bd6b833096ad3d0387191e");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "timers","stopwatches","laps","presets");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `timers`");
      _db.execSQL("DELETE FROM `stopwatches`");
      _db.execSQL("DELETE FROM `laps`");
      _db.execSQL("DELETE FROM `presets`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(TimerDao.class, TimerDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(StopwatchDao.class, StopwatchDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(LapDao.class, LapDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(PresetDao.class, PresetDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public TimerDao timerDao() {
    if (_timerDao != null) {
      return _timerDao;
    } else {
      synchronized(this) {
        if(_timerDao == null) {
          _timerDao = new TimerDao_Impl(this);
        }
        return _timerDao;
      }
    }
  }

  @Override
  public StopwatchDao stopwatchDao() {
    if (_stopwatchDao != null) {
      return _stopwatchDao;
    } else {
      synchronized(this) {
        if(_stopwatchDao == null) {
          _stopwatchDao = new StopwatchDao_Impl(this);
        }
        return _stopwatchDao;
      }
    }
  }

  @Override
  public LapDao lapDao() {
    if (_lapDao != null) {
      return _lapDao;
    } else {
      synchronized(this) {
        if(_lapDao == null) {
          _lapDao = new LapDao_Impl(this);
        }
        return _lapDao;
      }
    }
  }

  @Override
  public PresetDao presetDao() {
    if (_presetDao != null) {
      return _presetDao;
    } else {
      synchronized(this) {
        if(_presetDao == null) {
          _presetDao = new PresetDao_Impl(this);
        }
        return _presetDao;
      }
    }
  }
}
