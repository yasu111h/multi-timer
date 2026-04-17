package com.multitimer.app.data.db.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.multitimer.app.data.db.entity.LapEntity;
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
public final class LapDao_Impl implements LapDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<LapEntity> __insertionAdapterOfLapEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteLapsByStopwatchId;

  public LapDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfLapEntity = new EntityInsertionAdapter<LapEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `laps` (`id`,`stopwatch_id`,`lap_number`,`lap_millis`,`cumulative_millis`,`recorded_at`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final LapEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getStopwatchId());
        statement.bindLong(3, entity.getLapNumber());
        statement.bindLong(4, entity.getLapMillis());
        statement.bindLong(5, entity.getCumulativeMillis());
        statement.bindLong(6, entity.getRecordedAt());
      }
    };
    this.__preparedStmtOfDeleteLapsByStopwatchId = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM laps WHERE stopwatch_id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertLap(final LapEntity lap, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfLapEntity.insertAndReturnId(lap);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteLapsByStopwatchId(final long stopwatchId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteLapsByStopwatchId.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, stopwatchId);
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
          __preparedStmtOfDeleteLapsByStopwatchId.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<LapEntity>> getLapsByStopwatchId(final long stopwatchId) {
    final String _sql = "SELECT * FROM laps WHERE stopwatch_id = ? ORDER BY lap_number ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, stopwatchId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"laps"}, new Callable<List<LapEntity>>() {
      @Override
      @NonNull
      public List<LapEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStopwatchId = CursorUtil.getColumnIndexOrThrow(_cursor, "stopwatch_id");
          final int _cursorIndexOfLapNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "lap_number");
          final int _cursorIndexOfLapMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "lap_millis");
          final int _cursorIndexOfCumulativeMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "cumulative_millis");
          final int _cursorIndexOfRecordedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "recorded_at");
          final List<LapEntity> _result = new ArrayList<LapEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final LapEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpStopwatchId;
            _tmpStopwatchId = _cursor.getLong(_cursorIndexOfStopwatchId);
            final int _tmpLapNumber;
            _tmpLapNumber = _cursor.getInt(_cursorIndexOfLapNumber);
            final long _tmpLapMillis;
            _tmpLapMillis = _cursor.getLong(_cursorIndexOfLapMillis);
            final long _tmpCumulativeMillis;
            _tmpCumulativeMillis = _cursor.getLong(_cursorIndexOfCumulativeMillis);
            final long _tmpRecordedAt;
            _tmpRecordedAt = _cursor.getLong(_cursorIndexOfRecordedAt);
            _item = new LapEntity(_tmpId,_tmpStopwatchId,_tmpLapNumber,_tmpLapMillis,_tmpCumulativeMillis,_tmpRecordedAt);
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
  public Object getLapCount(final long stopwatchId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM laps WHERE stopwatch_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, stopwatchId);
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
