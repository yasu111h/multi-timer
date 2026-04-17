package com.multitimer.app.data.db.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.multitimer.app.data.db.Converters;
import com.multitimer.app.data.db.entity.StopwatchEntity;
import com.multitimer.app.data.db.entity.StopwatchStatus;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class StopwatchDao_Impl implements StopwatchDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<StopwatchEntity> __insertionAdapterOfStopwatchEntity;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<StopwatchEntity> __updateAdapterOfStopwatchEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteStopwatchById;

  public StopwatchDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfStopwatchEntity = new EntityInsertionAdapter<StopwatchEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `stopwatches` (`id`,`label`,`elapsed_millis`,`status`,`started_at`,`paused_at`,`current_lap_start_millis`,`lap_count`,`sort_order`,`created_at`,`updated_at`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final StopwatchEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getLabel());
        statement.bindLong(3, entity.getElapsedMillis());
        final String _tmp = __converters.fromStopwatchStatus(entity.getStatus());
        statement.bindString(4, _tmp);
        if (entity.getStartedAt() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getStartedAt());
        }
        if (entity.getPausedAt() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getPausedAt());
        }
        statement.bindLong(7, entity.getCurrentLapStartMillis());
        statement.bindLong(8, entity.getLapCount());
        statement.bindLong(9, entity.getSortOrder());
        statement.bindLong(10, entity.getCreatedAt());
        statement.bindLong(11, entity.getUpdatedAt());
      }
    };
    this.__updateAdapterOfStopwatchEntity = new EntityDeletionOrUpdateAdapter<StopwatchEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `stopwatches` SET `id` = ?,`label` = ?,`elapsed_millis` = ?,`status` = ?,`started_at` = ?,`paused_at` = ?,`current_lap_start_millis` = ?,`lap_count` = ?,`sort_order` = ?,`created_at` = ?,`updated_at` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final StopwatchEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getLabel());
        statement.bindLong(3, entity.getElapsedMillis());
        final String _tmp = __converters.fromStopwatchStatus(entity.getStatus());
        statement.bindString(4, _tmp);
        if (entity.getStartedAt() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getStartedAt());
        }
        if (entity.getPausedAt() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getPausedAt());
        }
        statement.bindLong(7, entity.getCurrentLapStartMillis());
        statement.bindLong(8, entity.getLapCount());
        statement.bindLong(9, entity.getSortOrder());
        statement.bindLong(10, entity.getCreatedAt());
        statement.bindLong(11, entity.getUpdatedAt());
        statement.bindLong(12, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteStopwatchById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM stopwatches WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertStopwatch(final StopwatchEntity stopwatch,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfStopwatchEntity.insertAndReturnId(stopwatch);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateStopwatch(final StopwatchEntity stopwatch,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfStopwatchEntity.handle(stopwatch);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteStopwatchById(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteStopwatchById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteStopwatchById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<StopwatchEntity>> getAllStopwatches() {
    final String _sql = "SELECT * FROM stopwatches ORDER BY sort_order ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"stopwatches"}, new Callable<List<StopwatchEntity>>() {
      @Override
      @NonNull
      public List<StopwatchEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "label");
          final int _cursorIndexOfElapsedMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "elapsed_millis");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "started_at");
          final int _cursorIndexOfPausedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "paused_at");
          final int _cursorIndexOfCurrentLapStartMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "current_lap_start_millis");
          final int _cursorIndexOfLapCount = CursorUtil.getColumnIndexOrThrow(_cursor, "lap_count");
          final int _cursorIndexOfSortOrder = CursorUtil.getColumnIndexOrThrow(_cursor, "sort_order");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final List<StopwatchEntity> _result = new ArrayList<StopwatchEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final StopwatchEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpLabel;
            _tmpLabel = _cursor.getString(_cursorIndexOfLabel);
            final long _tmpElapsedMillis;
            _tmpElapsedMillis = _cursor.getLong(_cursorIndexOfElapsedMillis);
            final StopwatchStatus _tmpStatus;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __converters.toStopwatchStatus(_tmp);
            final Long _tmpStartedAt;
            if (_cursor.isNull(_cursorIndexOfStartedAt)) {
              _tmpStartedAt = null;
            } else {
              _tmpStartedAt = _cursor.getLong(_cursorIndexOfStartedAt);
            }
            final Long _tmpPausedAt;
            if (_cursor.isNull(_cursorIndexOfPausedAt)) {
              _tmpPausedAt = null;
            } else {
              _tmpPausedAt = _cursor.getLong(_cursorIndexOfPausedAt);
            }
            final long _tmpCurrentLapStartMillis;
            _tmpCurrentLapStartMillis = _cursor.getLong(_cursorIndexOfCurrentLapStartMillis);
            final int _tmpLapCount;
            _tmpLapCount = _cursor.getInt(_cursorIndexOfLapCount);
            final int _tmpSortOrder;
            _tmpSortOrder = _cursor.getInt(_cursorIndexOfSortOrder);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new StopwatchEntity(_tmpId,_tmpLabel,_tmpElapsedMillis,_tmpStatus,_tmpStartedAt,_tmpPausedAt,_tmpCurrentLapStartMillis,_tmpLapCount,_tmpSortOrder,_tmpCreatedAt,_tmpUpdatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getStopwatchById(final long id,
      final Continuation<? super StopwatchEntity> $completion) {
    final String _sql = "SELECT * FROM stopwatches WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<StopwatchEntity>() {
      @Override
      @Nullable
      public StopwatchEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "label");
          final int _cursorIndexOfElapsedMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "elapsed_millis");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "started_at");
          final int _cursorIndexOfPausedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "paused_at");
          final int _cursorIndexOfCurrentLapStartMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "current_lap_start_millis");
          final int _cursorIndexOfLapCount = CursorUtil.getColumnIndexOrThrow(_cursor, "lap_count");
          final int _cursorIndexOfSortOrder = CursorUtil.getColumnIndexOrThrow(_cursor, "sort_order");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final StopwatchEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpLabel;
            _tmpLabel = _cursor.getString(_cursorIndexOfLabel);
            final long _tmpElapsedMillis;
            _tmpElapsedMillis = _cursor.getLong(_cursorIndexOfElapsedMillis);
            final StopwatchStatus _tmpStatus;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __converters.toStopwatchStatus(_tmp);
            final Long _tmpStartedAt;
            if (_cursor.isNull(_cursorIndexOfStartedAt)) {
              _tmpStartedAt = null;
            } else {
              _tmpStartedAt = _cursor.getLong(_cursorIndexOfStartedAt);
            }
            final Long _tmpPausedAt;
            if (_cursor.isNull(_cursorIndexOfPausedAt)) {
              _tmpPausedAt = null;
            } else {
              _tmpPausedAt = _cursor.getLong(_cursorIndexOfPausedAt);
            }
            final long _tmpCurrentLapStartMillis;
            _tmpCurrentLapStartMillis = _cursor.getLong(_cursorIndexOfCurrentLapStartMillis);
            final int _tmpLapCount;
            _tmpLapCount = _cursor.getInt(_cursorIndexOfLapCount);
            final int _tmpSortOrder;
            _tmpSortOrder = _cursor.getInt(_cursorIndexOfSortOrder);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new StopwatchEntity(_tmpId,_tmpLabel,_tmpElapsedMillis,_tmpStatus,_tmpStartedAt,_tmpPausedAt,_tmpCurrentLapStartMillis,_tmpLapCount,_tmpSortOrder,_tmpCreatedAt,_tmpUpdatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getStopwatchCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM stopwatches";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getRunningStopwatches(
      final Continuation<? super List<StopwatchEntity>> $completion) {
    final String _sql = "SELECT * FROM stopwatches WHERE status = 'RUNNING'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<StopwatchEntity>>() {
      @Override
      @NonNull
      public List<StopwatchEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "label");
          final int _cursorIndexOfElapsedMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "elapsed_millis");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "started_at");
          final int _cursorIndexOfPausedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "paused_at");
          final int _cursorIndexOfCurrentLapStartMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "current_lap_start_millis");
          final int _cursorIndexOfLapCount = CursorUtil.getColumnIndexOrThrow(_cursor, "lap_count");
          final int _cursorIndexOfSortOrder = CursorUtil.getColumnIndexOrThrow(_cursor, "sort_order");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final List<StopwatchEntity> _result = new ArrayList<StopwatchEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final StopwatchEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpLabel;
            _tmpLabel = _cursor.getString(_cursorIndexOfLabel);
            final long _tmpElapsedMillis;
            _tmpElapsedMillis = _cursor.getLong(_cursorIndexOfElapsedMillis);
            final StopwatchStatus _tmpStatus;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __converters.toStopwatchStatus(_tmp);
            final Long _tmpStartedAt;
            if (_cursor.isNull(_cursorIndexOfStartedAt)) {
              _tmpStartedAt = null;
            } else {
              _tmpStartedAt = _cursor.getLong(_cursorIndexOfStartedAt);
            }
            final Long _tmpPausedAt;
            if (_cursor.isNull(_cursorIndexOfPausedAt)) {
              _tmpPausedAt = null;
            } else {
              _tmpPausedAt = _cursor.getLong(_cursorIndexOfPausedAt);
            }
            final long _tmpCurrentLapStartMillis;
            _tmpCurrentLapStartMillis = _cursor.getLong(_cursorIndexOfCurrentLapStartMillis);
            final int _tmpLapCount;
            _tmpLapCount = _cursor.getInt(_cursorIndexOfLapCount);
            final int _tmpSortOrder;
            _tmpSortOrder = _cursor.getInt(_cursorIndexOfSortOrder);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new StopwatchEntity(_tmpId,_tmpLabel,_tmpElapsedMillis,_tmpStatus,_tmpStartedAt,_tmpPausedAt,_tmpCurrentLapStartMillis,_tmpLapCount,_tmpSortOrder,_tmpCreatedAt,_tmpUpdatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
