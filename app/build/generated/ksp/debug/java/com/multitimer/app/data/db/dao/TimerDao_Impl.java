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
import com.multitimer.app.data.db.entity.TimerEntity;
import com.multitimer.app.data.db.entity.TimerStatus;
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
public final class TimerDao_Impl implements TimerDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TimerEntity> __insertionAdapterOfTimerEntity;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<TimerEntity> __updateAdapterOfTimerEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteTimerById;

  private final SharedSQLiteStatement __preparedStmtOfUpdateSortOrder;

  public TimerDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTimerEntity = new EntityInsertionAdapter<TimerEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `timers` (`id`,`label`,`total_millis`,`remaining_millis`,`status`,`started_at`,`paused_at`,`sort_order`,`created_at`,`updated_at`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TimerEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getLabel());
        statement.bindLong(3, entity.getTotalMillis());
        statement.bindLong(4, entity.getRemainingMillis());
        final String _tmp = __converters.fromTimerStatus(entity.getStatus());
        statement.bindString(5, _tmp);
        if (entity.getStartedAt() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getStartedAt());
        }
        if (entity.getPausedAt() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getPausedAt());
        }
        statement.bindLong(8, entity.getSortOrder());
        statement.bindLong(9, entity.getCreatedAt());
        statement.bindLong(10, entity.getUpdatedAt());
      }
    };
    this.__updateAdapterOfTimerEntity = new EntityDeletionOrUpdateAdapter<TimerEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `timers` SET `id` = ?,`label` = ?,`total_millis` = ?,`remaining_millis` = ?,`status` = ?,`started_at` = ?,`paused_at` = ?,`sort_order` = ?,`created_at` = ?,`updated_at` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TimerEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getLabel());
        statement.bindLong(3, entity.getTotalMillis());
        statement.bindLong(4, entity.getRemainingMillis());
        final String _tmp = __converters.fromTimerStatus(entity.getStatus());
        statement.bindString(5, _tmp);
        if (entity.getStartedAt() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getStartedAt());
        }
        if (entity.getPausedAt() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getPausedAt());
        }
        statement.bindLong(8, entity.getSortOrder());
        statement.bindLong(9, entity.getCreatedAt());
        statement.bindLong(10, entity.getUpdatedAt());
        statement.bindLong(11, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteTimerById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM timers WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateSortOrder = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE timers SET sort_order = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertTimer(final TimerEntity timer, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfTimerEntity.insertAndReturnId(timer);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateTimer(final TimerEntity timer, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfTimerEntity.handle(timer);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteTimerById(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteTimerById.acquire();
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
          __preparedStmtOfDeleteTimerById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateSortOrder(final long id, final int order,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateSortOrder.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, order);
        _argIndex = 2;
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
          __preparedStmtOfUpdateSortOrder.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<TimerEntity>> getAllTimers() {
    final String _sql = "SELECT * FROM timers ORDER BY sort_order ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"timers"}, new Callable<List<TimerEntity>>() {
      @Override
      @NonNull
      public List<TimerEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "label");
          final int _cursorIndexOfTotalMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "total_millis");
          final int _cursorIndexOfRemainingMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "remaining_millis");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "started_at");
          final int _cursorIndexOfPausedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "paused_at");
          final int _cursorIndexOfSortOrder = CursorUtil.getColumnIndexOrThrow(_cursor, "sort_order");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final List<TimerEntity> _result = new ArrayList<TimerEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TimerEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpLabel;
            _tmpLabel = _cursor.getString(_cursorIndexOfLabel);
            final long _tmpTotalMillis;
            _tmpTotalMillis = _cursor.getLong(_cursorIndexOfTotalMillis);
            final long _tmpRemainingMillis;
            _tmpRemainingMillis = _cursor.getLong(_cursorIndexOfRemainingMillis);
            final TimerStatus _tmpStatus;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __converters.toTimerStatus(_tmp);
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
            final int _tmpSortOrder;
            _tmpSortOrder = _cursor.getInt(_cursorIndexOfSortOrder);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new TimerEntity(_tmpId,_tmpLabel,_tmpTotalMillis,_tmpRemainingMillis,_tmpStatus,_tmpStartedAt,_tmpPausedAt,_tmpSortOrder,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Object getTimerById(final long id, final Continuation<? super TimerEntity> $completion) {
    final String _sql = "SELECT * FROM timers WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<TimerEntity>() {
      @Override
      @Nullable
      public TimerEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "label");
          final int _cursorIndexOfTotalMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "total_millis");
          final int _cursorIndexOfRemainingMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "remaining_millis");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "started_at");
          final int _cursorIndexOfPausedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "paused_at");
          final int _cursorIndexOfSortOrder = CursorUtil.getColumnIndexOrThrow(_cursor, "sort_order");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final TimerEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpLabel;
            _tmpLabel = _cursor.getString(_cursorIndexOfLabel);
            final long _tmpTotalMillis;
            _tmpTotalMillis = _cursor.getLong(_cursorIndexOfTotalMillis);
            final long _tmpRemainingMillis;
            _tmpRemainingMillis = _cursor.getLong(_cursorIndexOfRemainingMillis);
            final TimerStatus _tmpStatus;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __converters.toTimerStatus(_tmp);
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
            final int _tmpSortOrder;
            _tmpSortOrder = _cursor.getInt(_cursorIndexOfSortOrder);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new TimerEntity(_tmpId,_tmpLabel,_tmpTotalMillis,_tmpRemainingMillis,_tmpStatus,_tmpStartedAt,_tmpPausedAt,_tmpSortOrder,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Object getRunningTimers(final Continuation<? super List<TimerEntity>> $completion) {
    final String _sql = "SELECT * FROM timers WHERE status = 'RUNNING'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<TimerEntity>>() {
      @Override
      @NonNull
      public List<TimerEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "label");
          final int _cursorIndexOfTotalMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "total_millis");
          final int _cursorIndexOfRemainingMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "remaining_millis");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "started_at");
          final int _cursorIndexOfPausedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "paused_at");
          final int _cursorIndexOfSortOrder = CursorUtil.getColumnIndexOrThrow(_cursor, "sort_order");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final List<TimerEntity> _result = new ArrayList<TimerEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TimerEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpLabel;
            _tmpLabel = _cursor.getString(_cursorIndexOfLabel);
            final long _tmpTotalMillis;
            _tmpTotalMillis = _cursor.getLong(_cursorIndexOfTotalMillis);
            final long _tmpRemainingMillis;
            _tmpRemainingMillis = _cursor.getLong(_cursorIndexOfRemainingMillis);
            final TimerStatus _tmpStatus;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __converters.toTimerStatus(_tmp);
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
            final int _tmpSortOrder;
            _tmpSortOrder = _cursor.getInt(_cursorIndexOfSortOrder);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new TimerEntity(_tmpId,_tmpLabel,_tmpTotalMillis,_tmpRemainingMillis,_tmpStatus,_tmpStartedAt,_tmpPausedAt,_tmpSortOrder,_tmpCreatedAt,_tmpUpdatedAt);
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

  @Override
  public Object getTimerCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM timers";
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
